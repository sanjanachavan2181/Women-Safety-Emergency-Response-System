-- Run in MySQL Command Line Client:  source schema.sql;
CREATE DATABASE IF NOT EXISTS women_safety;
USE women_safety;

CREATE TABLE IF NOT EXISTS users (
  user_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  mobile VARCHAR(15) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS emergency_contacts (
  contact_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  contact_name VARCHAR(100) NOT NULL,
  mobile VARCHAR(15) NOT NULL,
  relation VARCHAR(50),
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS sos_alerts (
  alert_id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  alert_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  latitude DOUBLE NULL,
  longitude DOUBLE NULL,
  status VARCHAR(80) NOT NULL DEFAULT 'Created',
  FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS safety_tips (
  tip_id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(150) NOT NULL,
  description TEXT NOT NULL,
  is_active TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS admins (
  admin_id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS admin_audit (
  audit_id INT AUTO_INCREMENT PRIMARY KEY,
  admin_id INT NOT NULL,
  action VARCHAR(100) NOT NULL,
  details VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


INSERT IGNORE INTO admins(username,password_hash) VALUES ('admin','65536:AAECAwQFBgcICQoLDA0ODw==:KgCG32zsYeYL2ZUca8RqEbVHZL8GanxoDlSDXXmo+78=');

INSERT INTO safety_tips(title,description) VALUES
('Share your live plans','Tell a trusted person your route and expected arrival time when travelling alone.'),
('Stay in well-lit areas','Prefer busy, well-lit roads and avoid isolated shortcuts at night.'),
('Keep your phone ready','Keep your phone charged and emergency contacts saved for quick access.');
