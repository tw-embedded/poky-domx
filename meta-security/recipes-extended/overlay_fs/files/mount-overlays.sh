#!/bin/sh
set -eu

OVERLAY_BASE="/mnt/overlay"
DIRS="var home root opt"

for d in $DIRS; do
    lower="/$d"
    upper="$OVERLAY_BASE/$d/upper"
    work="$OVERLAY_BASE/$d/work"

    if [ ! -d "$lower" ]; then
        echo "Lower directory $lower does not exist" >&2
        exit 1
    fi

    mkdir -p "$upper" "$work"

    if ! mountpoint -q "$lower"; then
        mount -t overlay overlay \
            -o "lowerdir=$lower,upperdir=$upper,workdir=$work" \
            "$lower"
    fi
done

