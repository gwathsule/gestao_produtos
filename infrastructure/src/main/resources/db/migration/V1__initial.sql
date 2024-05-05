CREATE TABLE products(
    id VARCHAR(36) NOT NULL,
    code INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    fabricated_at DATETIME(6),
    expired_at DATETIME(6),
    supplier_code VARCHAR(255),
    supplier_description VARCHAR(255),
    supplier_cnpj VARCHAR(14),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6)
)