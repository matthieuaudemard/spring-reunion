INSERT INTO USER (id, firstname, lastname, login, password)
VALUES (1, 'Matthieu', 'Audemard', 'taridev', 'matthieu'),
       (2, 'Julien', 'Peluso', 'julien', 'julien');

INSERT INTO MESSAGE (id, publication_date, sender_id, title, body)
VALUES (1, {ts '2019-05-19 14:12:001.0'}, 1, 'Qu''est-ce que le Lorem Ipsum?',
        'Le Lorem Ipsum est simplement du faux texte employé dans la composition et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard de l''imprimerie depuis les années 1500, quand un imprimeur anonyme assembla ensemble des morceaux de texte pour réaliser un livre spécimen de polices de texte. Il n''a pas fait que survivre cinq siècles, mais s''est aussi adapté à la bureautique informatique, sans que son contenu n''en soit modifié. Il a été popularisé dans les années 1960 grâce à la vente de feuilles Letraset contenant des passages du Lorem Ipsum, et, plus récemment, par son inclusion dans des applications de mise en page de texte, comme Aldus PageMaker.'),

       (2, {ts '2019-05-20 22:12:001.0'}, 2, 'Pourquoi l''utiliser?',
        'On sait depuis longtemps que travailler avec du texte lisible et contenant du sens est source de distractions, et empêche de se concentrer sur la mise en page elle-même. L''avantage du Lorem Ipsum sur un texte générique comme ''Du texte. Du texte. Du texte.'' est qu''il possède une distribution de lettres plus ou moins normale, et en tout cas comparable avec celle du français standard. De nombreuses suites logicielles de mise en page ou éditeurs de sites Web ont fait du Lorem Ipsum leur faux texte par défaut, et une recherche pour ''Lorem Ipsum'' vous conduira vers de nombreux sites qui n''en sont encore qu''à leur phase de construction. Plusieurs versions sont apparues avec le temps, parfois par accident, souvent intentionnellement (histoire d''y rajouter de petits clins d''oeil, voire des phrases embarassantes).');

INSERT INTO MESSAGE
VALUES (null, {ts '2019-05-20 08:30:043.0'}, 2, 'Un titre pour ajouter un peu d''authenticité au contenu',
        'You think water moves fast? You should see ice. It moves like it has a mind. Like it knows it killed the world once and got a taste for murder. After the avalanche, it took us a week to climb out. Now, I don''t know exactly when we turned on each other, but I know that seven of us survived the slide... and only five made it out. Now we took an oath, that I''m breaking now. We said we''d say it was the snow that killed the other two, but it wasn''t. Nature is lethal but it doesn''t hold a candle to man.');