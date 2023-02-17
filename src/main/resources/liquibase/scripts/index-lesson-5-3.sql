-- liquibase formatted sql

-- changeset yuri:1
CREATE INDEX student_name_index ON student (name);

-- changeset yuri:2
CREATE INDEX faculty_name_color_index ON faculty (name, color);