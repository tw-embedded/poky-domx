#
# Copyright OpenEmbedded Contributors
#
# SPDX-License-Identifier: MIT
#

# Helper class to pull in the right gtk-doc dependencies and configure
# gtk-doc to enable or disable documentation building (which requries the
# use of usermode qemu).

# This variable is set to True if api-documentation is in
# DISTRO_FEATURES and qemu-usermode is in MACHINE_FEATURES, and False otherwise.
#
# It should be used in recipes to determine whether gtk-doc based documentation should be built,
# so that qemu use can be avoided when necessary.
GTKDOC_ENABLED:class-native = "False"

# meson: default option name to enable/disable gtk-doc. This matches most
# project's configuration. In doubts - check meson_options.txt in project's
# source path.
GTKDOC_MESON_OPTION ?= 'docs'
GTKDOC_MESON_ENABLE_FLAG ?= 'true'
GTKDOC_MESON_DISABLE_FLAG ?= 'false'

# Auto enable/disable based on GTKDOC_ENABLED
EXTRA_OECONF:prepend:class-target = "${@bb.utils.contains('GTKDOC_ENABLED', 'True', '--enable-gtk-doc --enable-gtk-doc-html --disable-gtk-doc-pdf', \
                                                                                    '--disable-gtk-doc', d)} "
EXTRA_OEMESON:prepend:class-target = "-D${GTKDOC_MESON_OPTION}=${@bb.utils.contains('GTKDOC_ENABLED', 'True', '${GTKDOC_MESON_ENABLE_FLAG}', '${GTKDOC_MESON_DISABLE_FLAG}', d)} "

# When building native recipes, disable gtkdoc, as it is not necessary,
# pulls in additional dependencies, and makes build times longer
EXTRA_OECONF:prepend:class-native = "--disable-gtk-doc "
EXTRA_OECONF:prepend:class-nativesdk = "--disable-gtk-doc "
EXTRA_OEMESON:prepend:class-native = "-D${GTKDOC_MESON_OPTION}=${GTKDOC_MESON_DISABLE_FLAG} "
EXTRA_OEMESON:prepend:class-nativesdk = "-D${GTKDOC_MESON_OPTION}=${GTKDOC_MESON_DISABLE_FLAG} "

# Even though gtkdoc is disabled on -native, gtk-doc package is still
# needed for m4 macros.
DEPENDS:append = " gtk-doc-native"

# The documentation directory, where the infrastructure will be copied.
# gtkdocize has a default of "." so to handle out-of-tree builds set this to $S.
GTKDOC_DOCDIR ?= "${S}"

export STAGING_DIR_HOST

inherit python3native pkgconfig
DEPENDS:append = ""

do_configure:prepend () {
	# Need to use ||true as this is only needed if configure.ac both exists
	# and uses GTK_DOC_CHECK.
	gtkdocize --srcdir ${S} --docdir ${GTKDOC_DOCDIR} || true
}

do_compile:prepend:class-target () {
}
