SUMMARY = "Management suite for extremely large and complex data collections"
DESCRIPTION = "Unique technology suite that makes possible the management of \
extremely large and complex data collections"
HOMEPAGE = "https://www.hdfgroup.org/"
SECTION = "libs"

LICENSE = "HDF5"
LIC_FILES_CHKSUM = "file://COPYING;md5=ac1039f6bf7c9ab2b3693836f46d0735"

inherit cmake siteinfo

SRC_URI = " \
    https://support.hdfgroup.org/ftp/HDF5/releases/hdf5-1.13/hdf5-${PV}/src/${BPN}-${PV}.tar.bz2 \
    file://0002-Remove-suffix-shared-from-shared-library-name.patch \
    file://0001-cmake-remove-build-flags.patch \
"
SRC_URI[sha256sum] = "9c51b3da426977ec622a43dca8adaf4e81eabf838c1ff80c6225ad1d3ed54b5c"

FILES:${PN} += "${libdir}/libhdf5.settings ${datadir}/*"

EXTRA_OECMAKE = " \
    -DHDF5_INSTALL_CMAKE_DIR=${libdir}/cmake \
    -DCMAKE_INSTALL_PREFIX='${prefix}' \
    -DHDF5_INSTALL_LIB_DIR='${baselib}' \
"
EXTRA_OECMAKE:prepend:class-target = ""

do_install:append() {
    # Used for generating config files on target
    install -m 755 ${B}/bin/H5detect ${D}${bindir}
    install -m 755 ${B}/bin/H5make_libsettings ${D}${bindir}
}

BBCLASSEXTEND = "native"

SRC_DISTRIBUTE_LICENSES += "HDF5"

# h5fuse.sh script needs bash
RDEPENDS:${PN} += "bash"
