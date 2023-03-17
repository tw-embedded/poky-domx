# xen 4.17.0 release sha
SRCREV ?= "${AUTOREV}"

XEN_REL ?= "4.17"
XEN_BRANCH ?= "stable-${XEN_REL}"
XEN_MASTER ?= "master"

SRC_URI = " \
    git://github.com/tw-embedded/xen-4.17.git;protocol=https;branch=${XEN_MASTER} \
    file://0001-python-pygrub-pass-DISTUTILS-xen-4.15.patch \
    "

LIC_FILES_CHKSUM ?= "file://COPYING;md5=d1a1e216f80b6d8da95fec897d0dbec9"

PV = "${XEN_REL}+stable${SRCPV}"

S = "${WORKDIR}/git"

require xen.inc
require xen-tools.inc

