I – NULL / Conditions simples

1. Donner les offres dont le salaire maximum est NULL.
2. Donner les offres en télétravail (`remote = true`).
3. Donner les candidatures dont le statut est “PENDING”.
4. Donner les candidats créés aujourd’hui.
5. Donner les offres en `CDD`.
6. Donner les entreprises situées à Paris.
7. Donner les offres dont le salaire minimum est supérieur à 60 000.
8. Donner les offres dont la description contient “microservices”.
9. Donner les candidats dont le nom commence par ‘M’.
10. Donner les compétences contenant “Cloud”.

---

# II – Projection / Restriction

1. Donner les titres des offres chez “Google”.
2. Donner les offres dont le salaire est compris entre 50k et 70k.
3. Donner les candidats ayant une adresse Gmail.
4. Donner les entreprises triées par ville.
5. Donner les offres triées par salaire max décroissant.
6. Donner les offres en CDI et remote.
7. Donner les compétences associées à “DevOps Engineer AWS”.
8. Donner les candidats ayant postulé chez “Agate IT”.
9. Donner les offres créées aujourd’hui.
10. Donner les entreprises dont le nom contient “Tech”.

---

# III – Agrégations

1. Donner le nombre total d’offres.
2. Donner le nombre total de candidats.
3. Donner le nombre d’offres par entreprise.
4. Donner le nombre de candidatures par offre.
5. Donner le nombre de candidatures par statut
6. Donner le salaire moyen minimum des offres.
7. Donner le salaire moyen maximum par entreprise.
8. Donner l’entreprise ayant le plus d’offres.
9. Donner l’offre ayant le plus de candidatures.
10. Donner le nombre de compétences par offre.

---

# IV – Jointures

1. Donner les candidats et les offres auxquelles ils ont postulé.
2. Donner les candidats et les entreprises associées.
3. Donner les offres et leurs compétences.
4. Donner les entreprises et le nombre total de candidatures reçues.
5. Donner les candidats ayant postulé à au moins 2 offres.
6. Donner les offres n’ayant aucune candidature.
7. Donner les entreprises n’ayant aucune offre.
8. Donner les candidats ayant au moins une candidature ACCEPTED.
9. Donner les offres avec plus de 3 compétences.
10. Donner les offres et le nombre distinct de compétences.

---

# V – Sous-requêtes avancées

1. Donner les offres dont le salaire max est supérieur à la moyenne globale.
2. Donner les candidats ayant postulé à une offre avec le salaire le plus élevé.
3. Donner les entreprises dont le nombre d’offres est supérieur à la moyenne.
4. Donner les candidats ayant postulé à toutes les offres d’une entreprise donnée.
5. Donner les candidats ayant plus de candidatures que la moyenne globale.
6. Donner les offres ayant le même salaire minimum qu’une autre offre.
7. Donner les entreprises ayant au moins une offre sans candidature.
8. Donner les candidats n’ayant jamais postulé.