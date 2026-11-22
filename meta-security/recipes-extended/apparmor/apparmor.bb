SUMMARY = "Generic systemd loader for precompiled AppArmor binary policy caches"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://apparmor-cache-loader.service \
    file://load-apparmor-cache.sh \
"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "apparmor-cache-loader.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    install -d ${D}${sysconfdir}/apparmor/cache

    install -d ${D}${libexecdir}
    install -m 0755 ${WORKDIR}/load-apparmor-cache.sh ${D}${libexecdir}/load-apparmor-cache.sh

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/apparmor-cache-loader.service ${D}${systemd_system_unitdir}/apparmor-cache-loader.service
}

FILES:${PN} += " \
    ${sysconfdir}/apparmor \
    ${sysconfdir}/apparmor/cache \
    ${libexecdir}/load-apparmor-cache.sh \
    ${systemd_system_unitdir}/apparmor-cache-loader.service \
"

RDEPENDS:${PN} += "systemd"

