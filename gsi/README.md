# GSI assets

Drop your GSI images and optional files here before packaging or flashing.

Expected files:

- system.img
- vendor.img (optional)
- boot.img (optional)
- recovery.img (optional)
- readme.txt (optional)

Use the packaging script from the repository root:

```bash
python3 tools/package_gsi_release.py --input-dir gsi/out --output dist/LypeOS-gsi.7z
```
