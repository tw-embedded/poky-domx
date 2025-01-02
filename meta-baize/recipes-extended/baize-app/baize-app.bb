SUMMARY = "Baize App"
DESCRIPTION = "A sample app from GitHub"
LICENSE = "CLOSED"

inherit cmake
#useradd

#USERADD_PACKAGES = "${PN}"
#GROUPADD_PARAM = "--system domu"
#USERADD_PARAM = "--system --no-create-home --group domu domu"


#bitbake -c cleanall baize-app

SRC_URI = "git://github.com/tw-embedded/baize-app;branch=main;protocol=https \
           "

SRCREV = "aeb3c525ed1b64a58d1d597bf9a6346cfd9aa6e7"

S = "${WORKDIR}/git"

do_configure:prepend() {
    echo "::Current working directory: $(pwd)"
    echo "::Source directory: ${S}"
}

# use cmake install & append
do_install:append() {
    install -d ${D}${libdir}
    # only -dev version has .so
    install -m 0755 ${B}/lib/libbaize.so ${D}${libdir}/libbaize.so.1.0.0

    ln -sf libbaize.so.1.0.0 ${D}${libdir}/libbaize.so.1
    ln -sf libbaize.so.1 ${D}${libdir}/libbaize.so
}

FILES_${PN} = "${libdir}"


