SUMMARY = "Mount OverlayFS directories after /mnt/overlay is mounted"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit systemd

SRC_URI = " \
    file://mount-overlays.sh \
    file://mount-overlays.service \
"

S = "${WORKDIR}"

SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE:${PN} = "mount-overlays.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

RDEPENDS:${PN} += "util-linux"

do_install () {
    install -d ${D}${libexecdir}
    install -m 0755 ${WORKDIR}/mount-overlays.sh ${D}${libexecdir}/mount-overlays.sh

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/mount-overlays.service ${D}${systemd_system_unitdir}/mount-overlays.service
}

FILES:${PN} += " \
    ${libexecdir}/mount-overlays.sh \
    ${systemd_system_unitdir}/mount-overlays.service \
"

