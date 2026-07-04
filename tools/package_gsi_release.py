#!/usr/bin/env python3
"""Package a GSI release into a .7z archive (or .zip fallback)."""
from __future__ import annotations
import argparse
import os
import shutil
import subprocess
import sys
import tempfile
import zipfile
from pathlib import Path


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="Package Lype OS GSI release artifacts")
    parser.add_argument("--input-dir", required=True, help="Directory containing release artifacts")
    parser.add_argument("--output", required=True, help="Output archive path")
    parser.add_argument("--name", default="Lype OS GSI", help="Release name")
    parser.add_argument("--version", default="0.1.0", help="Release version")
    parser.add_argument("--description", default="Lype OS GSI release", help="Release description")
    return parser


def write_metadata(staging_dir: Path, name: str, version: str, description: str) -> None:
    metadata = f"""Name: {name}
Version: {version}
Description: {description}
Generated: {__import__('datetime').datetime.utcnow().strftime('%Y-%m-%d %H:%M:%SZ')}
"""
    (staging_dir / "release-info.txt").write_text(metadata, encoding="utf-8")


def copy_tree(input_dir: Path, staging_dir: Path) -> None:
    for root, dirs, files in os.walk(input_dir):
        rel_root = Path(root).relative_to(input_dir)
        target_root = staging_dir / rel_root
        target_root.mkdir(parents=True, exist_ok=True)
        for file_name in files:
            src = Path(root) / file_name
            dst = target_root / file_name
            shutil.copy2(src, dst)


def create_zip_archive(staging_dir: Path, output_path: Path) -> None:
    with zipfile.ZipFile(output_path, "w", compression=zipfile.ZIP_DEFLATED) as zf:
        for file_path in sorted(staging_dir.rglob("*")):
            if file_path.is_file():
                arcname = file_path.relative_to(staging_dir)
                zf.write(file_path, arcname.as_posix())


def create_7z_archive(staging_dir: Path, output_path: Path) -> None:
    seven_zip = shutil.which("7z") or shutil.which("7za")
    if not seven_zip:
        raise FileNotFoundError("7z/7za not found")
    cmd = [seven_zip, "a", "-t7z", "-mx9", str(output_path), "."]
    subprocess.run(cmd, cwd=str(staging_dir), check=True)


def main() -> int:
    args = build_parser().parse_args()
    input_dir = Path(args.input_dir).resolve()
    output_path = Path(args.output).resolve()
    if not input_dir.exists():
        print(f"Input directory not found: {input_dir}", file=sys.stderr)
        return 2
    output_path.parent.mkdir(parents=True, exist_ok=True)

    with tempfile.TemporaryDirectory(prefix="lype-gsi-", dir=str(output_path.parent)) as tmp_name:
        staging_dir = Path(tmp_name)
        copy_tree(input_dir, staging_dir)
        write_metadata(staging_dir, args.name, args.version, args.description)
        try:
            create_7z_archive(staging_dir, output_path)
            print(f"Created {output_path}")
        except FileNotFoundError:
            fallback = output_path.with_suffix(".zip")
            create_zip_archive(staging_dir, fallback)
            print(f"7z not available; created fallback archive {fallback}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
