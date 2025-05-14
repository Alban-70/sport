DROP TABLE IF EXISTS dailyChallenge;
DROP TABLE IF EXISTS challenges;
DROP TABLE IF EXISTS categorie;
DROP TABLE IF EXISTS color;
DROP TABLE IF EXISTS state;
DROP TABLE IF EXISTS profil;

CREATE TABLE profil(
    id_profil INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(50),
    prenom VARCHAR(50),
    mail VARCHAR(50),
    age INT,
    sexe VARCHAR(50),
    taille NUMERIC(4,2),
    poids NUMERIC(4,2),
    objectif VARCHAR(200),
    nb_jour INT
);

INSERT INTO profil(nom, prenom, mail) VALUES
('test', 'test', 'test');

CREATE TABLE state(
    id_etat INT AUTO_INCREMENT PRIMARY KEY,
    nom_etat VARCHAR(200)
);


CREATE TABLE color(
    id_color INT AUTO_INCREMENT PRIMARY KEY,
    nom_color VARCHAR(200)
);

CREATE TABLE categorie(
    id_categorie INT AUTO_INCREMENT PRIMARY KEY,
    nom_categorie VARCHAR(200),
    id_color INT,
    FOREIGN KEY (id_color) REFERENCES color(id_color)
);

CREATE TABLE challenges(
    id_challenges INT AUTO_INCREMENT PRIMARY KEY,
    nom_challenges VARCHAR(200),
    duree_challenges INT,
    categorie_id INT,
    minuteur INT,           -- minuteur = 0 --> pas besoin de minuteur, si egal à 1, on en a besoin
    duree INT,
    FOREIGN KEY (categorie_id) REFERENCES categorie(id_categorie)
);

CREATE TABLE dailyChallenge(
    id_daily INT AUTO_INCREMENT PRIMARY KEY,
    nom_challenge VARCHAR(200),
    id_challenge INT,
    profil_id INT,
    etat INT,
    FOREIGN KEY (id_challenge) REFERENCES challenges(id_challenges),
    FOREIGN KEY (profil_id) REFERENCES profil(id_profil),
    FOREIGN KEY (etat) REFERENCES state(id_etat)
);


INSERT INTO state(nom_etat) VALUES
('non complétés'),
('en cours'),
('complétés');


INSERT INTO color (nom_color) VALUES
('CRIMSON'),        -- cardio : énergie, intensité
('DARKSLATEBLUE'),  -- musculation : force, sérieux
('SANDYBROWN'),     -- souplesse : douceur, naturel
('MEDIUMSEAGREEN'), -- nutrition : sain, frais
('MIDNIGHTBLUE'),   -- sommeil : calme, nuit
('STEELBLUE'),      -- bien-être-mental : apaisant, sérieux
('GOLDENROD');      -- rituels-matinaux : lumière, positivité


INSERT INTO categorie (nom_categorie, id_color) VALUES
('cardio', 1),           -- id = 1
('musculation', 2),      -- id = 2
('souplesse', 3),        -- id = 3
('nutrition', 4),        -- id = 4
('sommeil', 5),          -- id = 5
('bien-être-mental', 6), -- id = 6
('rituels-matinaux', 7); -- id = 7



INSERT INTO challenges (nom_challenges, duree_challenges, categorie_id, minuteur, duree) VALUES
('Marcher 10 000 pas aujourd\'hui', 120, 1, 0, 0),
('Boire 2 litres d\'eau', 24, 4, 0, 0),
('Manger 5 fruits et légumes aujourd\'hui', NULL, 4, 0, 0),
('30 minutes de course à pied', 30, 1, 1, 30),
('Tenir une planche 1 minute', 1, 2, 1, 1),
('Faire 50 squats dans la journée', NULL, 2, 0, 0),
('Étirements matinaux pendant 15 minutes', 15, 3, 1, 15),
('Ne pas consommer de sucre aujourd''hui', 24, 4, 0, 0),
('Courir 5 km sans s\'arrêter', 60, 1, 0, 0),
('Aucune boisson sucrée pendant la journée', NULL, 4, 0, 0),
('Faire une séance de yoga', 60, 3, 0, 0),
('Monter 20 étages sans ascenseur', 60, 1, 0, 0),
('Marcher 5km sans téléphone', 120, 1, 0, 0),
('Lire 20 pages au lieu de scroll sur son téléphone', NULL, 6, 0, 0),
('Ne pas utiliser d\'écran après 21h', 180, 5, 0, 0),
('Aller au lit avant 22h', NULL, 5, 0, 0),
('Respiration guidée pendant 10 minutes', 10, 6, 1, 10),
('Journée 100% sans grignotage', NULL, 4, 0, 0),
('Se lever à 6h et marcher pendant 20 minutes', 20, 7, 1, 20);

SELECT * FROM challenges;
SELECT * FROM profil;
SELECT * FROM dailyChallenge WHERE profil_id = 1;
