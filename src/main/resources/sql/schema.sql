BEGIN;

-- CREATE DATABASE
-- DROP DATABASE sensor_sample_db;
CREATE DATABASE sensor_sample_db;

USE sensor_sample_db;

-- CREATE TABLES

-- アカウント
/*CREATE TABLE account
(
    id                INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    authkey_id        VARCHAR(100) NOT NULL,
    authkey_secret    VARCHAR(100) NOT NULL
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
*/
-- アプリケーション
CREATE TABLE application
(
    application_id    VARCHAR(100) PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    group_id          VARCHAR(100) NOT NULL,
    application_type  VARCHAR(100) NOT NULL,
    url               VARCHAR(1000) NOT NULL,
    token             VARCHAR(1000),
    KEY(application_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
/*
-- ルーター
CREATE TABLE router
(
    router_id             VARCHAR(100) PRIMARY KEY,
    registration_code     VARCHAR(100) NOT NULL,
    name                  VARCHAR(100) NOT NULL,
    executable_commands   VARCHAR(2000) NOT NULL DEFAULT '[]',
    group_id              VARCHAR(100) NOT NULL,
    KEY(router_id, registration_code)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- デバイス
CREATE TABLE device
(
    device_id         VARCHAR(100) NOT NULL PRIMARY KEY,
    registration_code VARCHAR(100) NOT NULL,
    name              VARCHAR(100) NOT NULL,
    group_id          VARCHAR(100) NOT NULL,
    KEY(device_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- センサー
CREATE TABLE sensor
(
    sensor_id             VARCHAR(100) PRIMARY KEY,
    name                  VARCHAR(100) NOT NULL,
    device_id             INT,
    executable_commands   VARCHAR(2000) NOT NULL DEFAULT '[]',
    KEY(sensor_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- グループ
CREATE TABLE group_inf
(
    group_id    VARCHAR(100) PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    is_default  BOOL DEFAULT false
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;*/

-- ルーターダウンリンク可能コマンド
/*CREATE TABLE enable_command_router
(
    router_id                 VARCHAR(100) PRIMARY KEY,
    update_router_firmware    BOOL,
    send_router_info          BOOL,
    set_filter_device         BOOL,
    clear_filter_device       BOOL,
    scan_device               BOOL,
    check_alive               BOOL,
    restart                   BOOL,
    power_off                 BOOL
) ENGINE = InnoDB DEFAULT  CHAR SET = utf8mb4;*/

-- センサーダウンリンク可能コマンド
/*CREATE TABLE enable_command_sensor
(
    sensor_id               VARCHAR(100) PRIMARY KEY,
    send_data_at_once       BOOL,
    set_uplink_interval     BOOL,
    set_uplink_term         BOOL,
    set_uplink_threshold    BOOL,
    set_sensor_range        BOOL,
    set_register            BOOL,
    download_data           BOOL,
    set_uplink_enable       BOOL,
    set_sensor_enable       BOOL,
    update_device_firmware  BOOL,
    restart                 BOOL,
    power_off               BOOL
) ENGINE = InnoDB DEFAULT  CHAR SET = utf8mb4;*/

-- センサーログ(RAW)
CREATE TABLE sensor_log
(
    id                  INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    application_id      VARCHAR(100) NOT NULL,
    application_name    VARCHAR(100) NOT NULL,
    router_id           VARCHAR(100) NOT NULL,
    router_imsi         VARCHAR(100) NOT NULL,
    router_rssi         INTEGER      NOT NULL,
    router_battery      INTEGER      NOT NULL,
    router_fw_version   VARCHAR(100) NOT NULL,
    device_id           VARCHAR(100) NOT NULL,
    sensor_id           VARCHAR(100) NOT NULL,
    sensor_name         VARCHAR(100) NOT NULL,
    device_rssi         INTEGER      NOT NULL,
    data                TEXT         NOT NULL,
    uplink_id           VARCHAR(100) NOT NULL,
    date                DATETIME     NOT NULL,
    json_string         TEXT,
    created_at          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY (application_id, router_id, device_id, sensor_id, date),
    KEY (date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 温度データログ
CREATE TABLE temperature
(
    id              INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    router_id       VARCHAR(100) NOT NULL,
    device_id       VARCHAR(100) NOT NULL,
    device_rssi     INTEGER      NOT NULL,
    sensor_id       VARCHAR(100) NOT NULL,
    sensor_name     VARCHAR(100) NOT NULL,
    value           DOUBLE NOT NULL,
    uplink_id       VARCHAR(100) NOT NULL,
    date            DATETIME NOT NULL,
    create_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY (device_id, sensor_id, uplink_id, date),
    KEY (date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 湿度データログ
CREATE TABLE humidity
(
    id          INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    router_id   VARCHAR(100) NOT NULL,
    device_id   VARCHAR(100) NOT NULL,
    device_rssi INTEGER      NOT NULL,
    sensor_id   VARCHAR(100) NOT NULL,
    value       DOUBLE       NOT NULL,
    uplink_id   VARCHAR(100) NOT NULL,
    date        DATETIME     NOT NULL,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY (router_id, device_id, sensor_id, date),
    KEY (date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 照度データログ
CREATE TABLE lux
(
    id          INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    router_id   VARCHAR(100) NOT NULL,
    device_id   VARCHAR(100) NOT NULL,
    device_rssi INTEGER      NOT NULL,
    sensor_id   VARCHAR(100) NOT NULL,
    value       DOUBLE       NOT NULL,
    uplink_id   VARCHAR(100) NOT NULL,
    date        DATETIME     NOT NULL,
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY (router_id, device_id, sensor_id, date),
    KEY (date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 加速度データログ(振動検知)
CREATE TABLE move_detection
(
    id          INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    router_id   VARCHAR(100)     NOT NULL,
    device_id   VARCHAR(100)     NOT NULL,
    device_rssi INTEGER          NOT NULL,
    sensor_id   VARCHAR(100)     NOT NULL,
    is_detected BOOLEAN          NOT NULL,
    count       INTEGER UNSIGNED NOT NULL,
    uplink_id   VARCHAR(100)     NOT NULL,
    date        DATETIME         NOT NULL,
    created_at  DATETIME         NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY (router_id, device_id, sensor_id, date),
    KEY (date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;