SUMMARY = "Objects and helper methods to help reading and writing AppStream metadata"
HOMEPAGE = "https://people.freedesktop.org/~hughsient/appstream-glib/index.htm"
LICENSE = "LGPL-2.1-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

DEPENDS = " \
    gperf-native \
    glib-2.0 \
    gtk+3 \
    libyaml \
    libarchive \
    libsoup-2.4 \
    json-glib \
    gdk-pixbuf \
    freetype \
    fontconfig \
    libstemmer \
"

inherit meson gobject-introspection gettext bash-completion pkgconfig manpages

SRC_URI = "https://people.freedesktop.org/~hughsient/${BPN}/releases/${BP}.tar.xz"
SRC_URI[sha256sum] = "ca1ed22e3bde3912cb903aaa7de085d55771da454f1c0573fd9608e1de9c4002"

EXTRA_OEMESON = " \
    -Drpm=false \
"

PACKAGECONFIG ?= ""
PACKAGECONFIG[manpages] = "-Dman=true,-Dman=false,docbook-xml-dtd4-native docbook-xsl-stylesheets-native libxslt-native"

FILES:${PN} += "${libdir}/asb-plugins-5"

FILES:${PN}-dev += " \
    ${datadir}/installed-tests \
    ${datadir}/gettext \
"

BBCLASSEXTEND = "native"
