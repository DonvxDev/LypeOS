#!/bin/bash
# Generiert bootanimation.zip für Lype OS aus PNG-Frames in part0/ und part1/
set -e
DIR="$(cd "$(dirname "$0")/.." && pwd)/bootanimation"
cd "$DIR"
if [ ! -d part0 ]; then
  echo "Erstelle Platzhalter-Frames..."
  mkdir -p part0 part1
  for i in $(seq -w 0 59); do
    convert -size 1080x2400 xc:'#6C5CE7' -gravity center \
      -pointsize 72 -fill white -annotate 0 'Lype OS' "part0/frame_${i}.png" 2>/dev/null || \
      echo "ImageMagick nicht installiert – PNG-Frames manuell in part0/ ablegen"
    break
  done
fi
zip -0qry bootanimation.zip part0 2>/dev/null || zip -0qry bootanimation.zip .
cp -f bootanimation.zip bootanimation-dark.zip
echo "Bootanimation erstellt: $DIR/bootanimation.zip"
