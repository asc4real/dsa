#!/usr/bin/env bash
set -euo pipefail
URL=${BASE_URL:-http://localhost:8080}
curl --fail --silent --show-error "$URL/actuator/health" | tee /dev/stderr
echo 'Protected API smoke test requires JWT from actual configured OIDC issuer.'
