#!/usr/bin/env python3
from pathlib import Path
files=sorted(Path('src/main/resources/db/migration').glob('V*.sql'))
assert len(files)==12,len(files)
assert [p.name[:4] for p in files]==[f'V{i:03d}' for i in range(1,13)]
s=files[6].read_text()
assert 'EXCLUDE USING gist' in s and "tstzrange(reserved_start_at,reserved_end_at,'[)')" in s
print('Migration sequence and required overlap constraint present; database execution not performed')
