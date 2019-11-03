#! /bin/bash

__FILE__="$(readlink -e "${BASH_SOURCE[0]}")"
__DIR__="$(dirname "${__FILE__}")"

APP_ROOT="$(dirname "${__DIR__}")"
ENV_FILE="${APP_ROOT}/.env"

if [[ ! -f "${ENV_FILE}" ]]; then
  echo "File ${ENV_FILE} should exists"
  exit 1
fi

# shellcheck disable=SC1090
. "${ENV_FILE}"

APP_CODE="${APP_CODE}"
WEB_HOSTNAME="${WEB_HOSTNAME}"
WEB_SERVER_PORT="${WEB_SERVER_PORT}"

cat <<EOT
server {
    listen      80;
    server_name ${WEB_HOSTNAME};
    root        ${APP_ROOT}/public;
    index       index.html;

    access_log  /var/log/nginx/${APP_CODE}.frontend.access.log;
    error_log   /var/log/nginx/${APP_CODE}.frontend.error.log;

    client_max_body_size 128M;

    location ~ /\.(git|svn|ht) {
        deny all;
    }

    location /api/ {
        proxy_http_version   1.1;
        proxy_set_header     Upgrade \$http_upgrade;
        proxy_set_header     Connection "upgrade";
        proxy_set_header     X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header     Host \$host;
        proxy_buffering      off;
        proxy_pass           http://127.0.0.1:${WEB_SERVER_PORT};
    }
}
EOT
