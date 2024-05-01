Us : Attaché de classe :
## les cours du professeurs ✅
    GET http://localhost:9998/api/cours/professeur/4

## visualiser les sessions de cours qui lui sont programmés ✅
    GET http://localhost:9998/api/sessions/professeur/4

## Filtrer ses cours par period : a revoir
    GET http://localhost:9998/api/sessions/professeur/4?page=0&size=5&period=jour

## lister le nombre d'heures de cours qu’il a effectuées durant le mois et peut éventuellement filtrer par module.
    GET http://localhost:9998/api/sessions/professeur/4/total-hours?month=2024-05

Us :  RP
## Lister cours planifiés et de filtrer par état(Terminé ou En Cours )
    GET http://localhost:9998/api/rp/cours?etat=EN_COURS

## Visualiser les sessions de cours qui ont été faites ( valide)
    GET http://localhost:9998/api/rp/sessions

## voir les étudiants qui suivent le même cours et peut filtrer par classe
GET http://localhost:9998/api/rp/session/4/etudiant?libelle=L1GLRS


```json
{
 "nbreHeureGlobal": 80,
  "module": {
    "id": 3
  },
  "semestre": {
    "id": 2
  },
  "professeur": {
    "id": 4
   }

```

// les endpoints pour le moments :
#GET http://localhost:9998/api/sessions
#http://localhost:9998/api/sessions/professeur/4
#http://localhost:9998/api/sessions/professeur/4?page=0&size=5&period=jour
#http://localhost:9998/api/sessions/professeur/3?page=0&size=5&period=semaine
#http://localhost:9998/api/cours/professeur/4
#http://localhost:9998/api/professeurs

## Ajoute de cours
```json
POST http://localhost:9998/api/rp/cours
Content-Type: application/json

{
  "nbreHeureGlobal": 75,
  "module": {
    "libelle": "UI/UX DESIGN",
    "isActive": true
  },
  "semestre": {
    "id": 9
  },
  "professeur": {
    "prenom": "BADARA",
    "nom": "MBENGUE",
    "isActive": true,
    "specialite": "INFORMATIQUE",
    "grade": "L2"
  }
}

```


## Creer annee Scolaire:
```json
POST http://localhost:9998/api/rp/anneeScolaires
Content-Type: application/json

{
  "libelle": "2027-2028",
  "finDePeriod": "2028-08-31"
}

```


## AnneeScolaire by libelle
    GET http://localhost:9998/api/rp/anneeScolaires/2027-2028/libelle

## Selectionner une anneeScolaire en le metant a true :
    PUT http://localhost:9998/api/rp/anneeScolaires/2027-2028/libelle

## Ajout salle

```json
  POST http://localhost:9998/api/rp/salles
  Content-Type: application/json

    {
      "nom": "Los chicas",
      "nbrPlace": 30,
      "number": "400"
    }

```



## Ajout classe
```json
    POST http://localhost:9998/api/rp/classes
    Content-Type: application/json

    {
      "libelle": "M2DOMAINE"
    }

```
    

## Ajout semestre
```json
    POST http://localhost:9998/api/rp/semestres
    Content-Type: application/json
    {
    "libelle": "SEMESTRE 45"
    }
```

## Ajout module

```json
POST http://localhost:9998/api/rp/modules
   Content-Type: application/json
    {
      "libelle": "FLUTTER ET ANGULAR"
    }
```

## planification session de cours

"salle": {
     "nom": "A101",
     "numero": 1,
     "nombrePlaces": 50
}
```json
POST http://localhost:9998/api/rp/sessions/77
Content-Type: application/json
        
{
    "date": "2024-05-15",
    "heureDebut": "09:00:00",
    "heureFin": "11:00:00",
    "typeSession": "PRESENTIEL",
    "salle": {
      "id": 3
    }
}
```



