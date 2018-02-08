DROP TABLE IF EXISTS form_submission_sectors;
DROP TABLE IF EXISTS form_submissions;
DROP TABLE IF EXISTS sectors;

CREATE TABLE sectors (
  id        BIGINT AUTO_INCREMENT,
  name      VARCHAR(255),
  parent_id BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE form_submissions (
  id   BIGINT AUTO_INCREMENT,
  name VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE form_submission_sectors (
  sector_id          BIGINT NOT NULL,
  form_submission_id BIGINT NOT NULL
);

ALTER TABLE form_submission_sectors
  ADD CONSTRAINT FK_FORM_SUBMISSION_SECTORS_SUBMISSION_ID FOREIGN KEY (form_submission_id) REFERENCES form_submissions (id);
ALTER TABLE form_submission_sectors
  ADD CONSTRAINT FK_FORM_SUBMISSION_SECTORS_SECTOR_ID FOREIGN KEY (sector_id) REFERENCES sectors (id);
ALTER TABLE sectors
  ADD CONSTRAINT FK_SECTOR_PARENT_ID FOREIGN KEY (parent_id) REFERENCES sectors (id);

SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO sectors (id, parent_id, name) VALUES (1, NULL, 'Manufacturing');
INSERT INTO sectors (id, parent_id, name) VALUES (19, 1, 'Construction materials');
INSERT INTO sectors (id, parent_id, name) VALUES (6, 1, 'Food and Beverage');
INSERT INTO sectors (id, parent_id, name) VALUES (342, 6, 'Bakery & confectionery products');
INSERT INTO sectors (id, parent_id, name) VALUES (43, 6, 'Beverages');

SET FOREIGN_KEY_CHECKS = 1;