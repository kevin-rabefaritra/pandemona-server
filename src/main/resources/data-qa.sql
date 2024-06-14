INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('1', '47.507905', '-18.879190', '2024-06-07 15:29:00.000000', 0, '2', '2024-06-07 15:29:00.000000', 'Pharmacie Centrale', 'Rue de la Gare, Antananarivo.', '020 22 334 45;020 22 335 55', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('0', '49.402981', '-18.146240', '2024-06-07 15:29:04.000000', 0, '3', '2024-06-07 15:29:04.000000', 'Pharmacie de la Mer', 'Boulevard de l\'Océan, Toamasina.', '020 53 667 89;020 53 668 99', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('1', '44.317560', '-20.283330', '2024-06-07 15:29:08.000000', 0, '4', '2024-06-07 15:29:08.000000', 'Pharmacie des Tsingy', 'Avenue de l\'Indépendance, Morondava.', '020 95 123 45;020 95 124 55', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('0', '49.283330', '-12.283330', '2024-06-07 15:29:12.000000', 0, '5', '2024-06-07 15:29:12.000000', 'Pharmacie du Soleil', 'Route de Majunga, Antsiranana.', '020 82 778 99;020 82 779 99', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('1', '47.433330', '-19.383330', '2024-06-07 15:29:16.000000', 0, '6', '2024-06-07 15:29:16.000000', 'Pharmacie des Volcans', 'Rue des Volcans, Ambatolampy.', '020 44 556 77;020 44 557 77', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('1', '47.085100', '-21.454400', '2024-06-07 15:29:20.000000', 0, '7', '2024-06-07 15:29:20.000000', 'Pharmacie de la Plaine', 'Route de la Plaine, Fianarantsoa.', '020 75 999 88;020 75 998 88', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('1', '46.345000', '-15.670000', '2024-06-07 15:29:24.000000', 0, '8', '2024-06-07 15:29:24.000000', 'Pharmacie de la Baie', 'Avenue de la Baie, Mahajanga.', '020 62 444 22;020 62 445 22', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('0', '47.033333', '-19.866667', '2024-06-07 15:29:28.000000', 0, '9', '2024-06-07 15:29:28.000000', 'Pharmacie des Lacs', 'Rue des Lacs, Antsirabe.', '020 22 555 33;020 22 556 33', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('2', '45.345000', '-14.670000', '2024-06-07 15:29:32.000000', 0, '10', '2024-06-07 15:29:32.000000', 'Pharmacie de l\'Étoile', 'Avenue de l\'Étoile, Sambava.', '020 63 333 44;020 63 334 44', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('3', '46.450000', '-13.450000', '2024-06-07 15:29:36.000000', 0, '11', '2024-06-07 15:29:36.000000', 'Pharmacie des Palmiers', 'Boulevard des Palmiers, Nosy Be.', '020 64 222 55;020 64 223 55', NULL);
INSERT INTO `drugstores` (`city`, `latitude`, `longitude`, `createddate`, `deleted`, `id`, `lastmodifieddate`, `name`, `address`, `contacts`, `features`) VALUES ('2', '47.555000', '-22.444000', '2024-06-07 15:29:40.000000', 0, '12', '2024-06-07 15:29:40.000000', 'Pharmacie des Îles', 'Route des Îles, Fort-Dauphin.', '020 65 888 66;020 65 889 66', NULL);

INSERT INTO `ondutydrugstores` (`id`, `createddate`, `lastmodifieddate`, `enddate`, `startdate`, `deleted`) VALUES (2, '2024-06-13 12:12:04.000000', '2024-06-13 12:12:04.000000', '2024-06-21', '2024-06-15', b'0');
INSERT INTO `ondutydrugstores` (`id`, `createddate`, `lastmodifieddate`, `enddate`, `startdate`, `deleted`) VALUES (3, '2024-06-13 12:12:04.000000', '2024-06-13 12:12:04.000000', '2024-06-15', '2024-06-07', b'0');
INSERT INTO `ondutydrugstores` (`id`, `createddate`, `lastmodifieddate`, `enddate`, `startdate`, `deleted`) VALUES (4, '2024-06-13 12:12:04.000000', '2024-06-13 12:12:04.000000', '2024-06-01', '2024-06-07', b'0');

INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('2', '2');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('2', '3');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('2', '4');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('2', '5');

INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('3', '6');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('3', '7');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('3', '8');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('3', '9');

INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('4', '10');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('4', '11');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('4', '12');
INSERT INTO `ondutydrugstores_drugstores` (`on_duty_drugstores_id`, `drugstores_id`) VALUES ('4', '2');
