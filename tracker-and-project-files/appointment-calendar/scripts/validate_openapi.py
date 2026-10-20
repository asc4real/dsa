#!/usr/bin/env python3
from pathlib import Path
try:
 import yaml
except ImportError:
 raise SystemExit('Install PyYAML to validate the OpenAPI file')
spec=yaml.safe_load(Path('openapi/appointment-calendar.yaml').read_text())
ops=[]
for p, methods in spec['paths'].items():
 for method, data in methods.items():
  if method.lower() in {'get','post','patch','put','delete'}:ops.append(data['operationId'])
assert len(ops)==67,(len(ops),'Expected 67 operations')
assert len(set(ops))==len(ops),'Duplicate operationId'
for op in ops:
 source='\n'.join(p.read_text() for p in Path('src/main/java').rglob('*ApiController.java'))
 assert f' {op}(' in source, f'Missing controller method: {op}'
print('OpenAPI routes:',len(ops),'all controller names mapped')
