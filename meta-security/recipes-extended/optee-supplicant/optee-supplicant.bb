SUMMARY = "optee supplicant service"
DESCRIPTION = "optee supplicant service"
LICENSE = "CLOSED"

inherit cmake systemd

SRC_URI = "git://github.com/tw-embedded/optee_client;branch=master;protocol=https \
           "

SRCREV = "6486773583b5983af8250a47cf07eca938e0e422"

S = "${WORKDIR}/git"

CMAKE_INSTALL_PREFIX = "/usr"
CMAKE_INSTALL_SBINDIR = "sbin"
CMAKE_INSTALL_SYSCONFDIR = "/etc"

CFG_TEE_SUPPL_USER = "root"
CFG_TEE_SUPPL_GROUP = "root"

do_configure() {
    sed -e "s|@CFG_TEE_GROUP@|root|" \
        -e "s|@CFG_TEEPRIV_GROUP@|root|" \
        ${S}/tee-supplicant/optee-udev.rules.in > ${WORKDIR}/optee-udev.rules

    sed -e "s|@CMAKE_INSTALL_PREFIX@|${CMAKE_INSTALL_PREFIX}|" \
        -e "s|@CMAKE_INSTALL_SBINDIR@|${CMAKE_INSTALL_SBINDIR}|" \
        -e "s|@CMAKE_INSTALL_SYSCONFDIR@|${CMAKE_INSTALL_SYSCONFDIR}|" \
        -e "s|@CFG_TEE_SUPPL_USER@|${CFG_TEE_SUPPL_USER}|" \
        -e "s|@CFG_TEE_SUPPL_GROUP@|${CFG_TEE_SUPPL_GROUP}|" \
        ${S}/tee-supplicant/tee-supplicant@.service.in > ${WORKDIR}/tee-supplicant@.service
}

do_compile() {
}

do_install() {
    install -d ${D}${nonarch_base_libdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/optee-udev.rules ${D}${nonarch_base_libdir}/udev/rules.d/99-optee-udev.rules

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/tee-supplicant@.service ${D}${systemd_system_unitdir}/tee-supplicant@.service
}

FILES:${PN} += "${systemd_system_unitdir} \
                ${libdir} \
                "

