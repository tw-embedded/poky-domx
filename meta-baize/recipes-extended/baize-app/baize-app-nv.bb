SUMMARY = "Baize App non-version"
DESCRIPTION = "A sample app from GitHub"
LICENSE = "CLOSED"

inherit cmake
#useradd

#USERADD_PACKAGES = "${PN}"
#GROUPADD_PARAM = "--system domu"
#USERADD_PARAM = "--system --no-create-home --group domu domu"


#bitbake -c cleanall baize-app_nv

SRC_URI = "git://github.com/tw-embedded/baize-app;branch=main;protocol=https \
           "

SRCREV = "596f797147e2e7b78c2bcf7babde2120f1d89c0d"

S = "${WORKDIR}/git"

do_configure:prepend() {
    echo "::Current working directory: $(pwd)"
    echo "::Source directory: ${S}"
}

SOLIBS = ".so .so.*"
FILES_SOLIBSDEV = ""

# use cmake install & append
do_install:append() {
    install -d ${D}${libdir}
    install -m 0755 ${B}/lib/libbaize.so ${D}${libdir}/libbaize.so

    install -m 0755 ${S}/sdk/libsdk.so ${D}${libdir}/libsdk.so
}

FILES_${PN} = "${libdir}"

