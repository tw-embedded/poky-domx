
TOOLCHAIN_TEST_TARGET ??= "user"
TOOLCHAIN_TEST_HOST ??= "localhost"
TOOLCHAIN_TEST_HOST_USER ??= "root"
TOOLCHAIN_TEST_HOST_PORT ??= "2222"

MAKE_CHECK_BOARDFLAGS ??= ""
MAKE_CHECK_BOARDARGS ??= "--target_board=${TOOLCHAIN_TEST_TARGET}${MAKE_CHECK_BOARDFLAGS}"

python () {
    # Provide the targets compiler args via targets options. This allows dejagnu to
    # correctly mark incompatible tests as UNSUPPORTED (e.g. needs soft-float
    # but running on hard-float target).
    #
    # These options are called "multilib_flags" within the gcc test suite. Most
    # architectures handle these options in a sensible way such that tests that
    # are incompatible with the provided multilib are marked as UNSUPPORTED.
    #
    # Note: multilib flags are added to the compile command after the args
    # provided by any test (through dg-options), CFLAGS_FOR_TARGET is always
    # added to the compile command before any other args but is not interpted
    # as options like multilib flags.
    #
    # i686, x86-64 and aarch64 are special, since most toolchains built for
    # these targets don't do multilib the tests do not get correctly marked as
    # UNSUPPORTED. More importantly the test suite itself does not handle
    # overriding the multilib flags where it could (like other archs do). As
    # such do not pass the target compiler args for these targets.
    args = d.getVar("TUNE_CCARGS").split()
    if d.getVar("TUNE_ARCH") in ["i686", "x86_64", "aarch64"]:
        args = []
    d.setVar("MAKE_CHECK_BOARDFLAGS", ("/" + "/".join(args)) if len(args) != 0 else "")
}

python check_prepare() {
    def generate_remote_ssh_linux_config(d):
        content = []
        content.append('load_generic_config "unix"')
        content.append('process_multilib_options ""')
        content.append("set_board_info hostname {0}".format(d.getVar("TOOLCHAIN_TEST_HOST")))
        content.append("set_board_info username {0}".format(d.getVar("TOOLCHAIN_TEST_HOST_USER")))

        port = d.getVar("TOOLCHAIN_TEST_HOST_PORT")
        content.append("set_board_info rsh_prog \"/usr/bin/ssh -p {0} -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no\"".format(port))
        content.append("set_board_info rcp_prog \"/usr/bin/scp -P {0} -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no\"".format(port))

        return "\n".join(content)

    dejagnudir = d.expand("${WORKDIR}/dejagnu")
    if not os.path.isdir(dejagnudir):
        os.makedirs(dejagnudir)

    # write out target ssh board config
    with open(os.path.join(dejagnudir, "ssh.exp"), "w") as f:
        f.write(generate_remote_ssh_linux_config(d))

    # generate site.exp to provide boards
    with open(os.path.join(dejagnudir, "site.exp"), "w") as f:
        f.write("lappend boards_dir {0}\n".format(dejagnudir))
        f.write("set CFLAGS_FOR_TARGET \"{0}\"\n".format(d.getVar("TOOLCHAIN_OPTIONS")))
}

