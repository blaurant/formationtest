
# US Story Log

## Epic : Transport de Matière du Centre de surtri au Recycleur

### US000
En tant que **centre de surtri**, j'ai une vue de mon stock sous forme de liste de (qualité - poid)

### US001
En tant que **centre de surtri**, je créé une **demande d'enlèvement**. Je renseigne une **qualité** de matière, une **date de mise à disposition** et une **chaine**.
La **chaine** comporte : **Le centre de surtri**, **Transporteur**, **Qualité**, **Recycleur**.

### US002
En tant que **Transporteur**, sur une **demande d'enlèvement** en état "**enlèvement demandé**", 
je renseigne le transport effectué avec une **date de collecte** et une **date de livraison**).

### US003
En tant que **Recycleur**, je réceptionne la **Qualité**. Je renseigne une **date de réception**, une **qualité** et un **poid**.

### US004
En tant que **Opérateur**, lorsqu'une **transaction** est dans un état "**transport reçu**", je génère un **Bon A Facturer**.<p>
![Pricing of Transport Formula](/assets/images/TransportPricing.jpeg "Formula for Transport calculation")

### US005
En tant que **transporteur**, j'accepte la **demande d'enlèvement** faite par le **CDST**.
La transaction passe en état "demande d'enlèvement validé"

En tant que **citeo**, j'accepte la (**demande d'enlèvement/transport/reception**) faite par le **CDST/transporteur/recycleur**.
La transaction passe en état "demande d'enlèvement/transport/reception validé"
### End Of Sprint 1 (POC 1 semaine)

### US006
En tant que centre de sur-tri, lors de demande d’enlèvement je peux ajouter un document dans la transaction.  
Idem pour le recycleur lors de la réception.

### US006
Upload de document dans la transaction pour le transporteur et le recycleur

### US007
Baf pour un transporteur entre deux dates

### US008
Choix dans les chaines du transporteur et du recycleur lors de la demande d'enlèvement
### US007
Baf pour un transporteur entre deux dates

### US008
Choix dans les chaines du transporteur et du recycleur lors de la demande d'enlèvement
### End Of Sprint 2 (POC 1 semaine)
