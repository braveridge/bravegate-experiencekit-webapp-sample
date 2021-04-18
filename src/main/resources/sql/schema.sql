BEGIN;

-- CREATE DATABASE
-- DROP DATABASE sensor_sample_db;
CREATE DATABASE IF NOT EXISTS sensor_sample_db;

USE sensor_sample_db;

-- CREATE TABLES
-- アプリケーション
CREATE TABLE IF NOT EXISTS application
(
    application_id    VARCHAR(100) PRIMARY KEY,
    name              VARCHAR(100) NOT NULL,
    group_id          VARCHAR(100) NOT NULL,
    application_type  VARCHAR(100) NOT NULL,
    url               VARCHAR(1000) NOT NULL,
    token             VARCHAR(1000),
    KEY(application_id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- センサーログ(RAW)
CREATE TABLE IF NOT EXISTS sensor_log
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
CREATE TABLE IF NOT EXISTS temperature
(
    id              INTEGER UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    router_id       VARCHAR(100) NOT NULL,
    device_id       VARCHAR(100) NOT NULL,
    device_rssi     INTEGER      NOT NULL,
    sensor_id       VARCHAR(100) NOT NULL,
    value           DOUBLE NOT NULL,
    uplink_id       VARCHAR(100) NOT NULL,
    date            DATETIME NOT NULL,
    create_at       DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY (device_id, sensor_id, uplink_id, date),
    KEY (date)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 湿度データログ
CREATE TABLE IF NOT EXISTS humidity
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
CREATE TABLE IF NOT EXISTS lux
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
CREATE TABLE IF NOT EXISTS move_detection
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