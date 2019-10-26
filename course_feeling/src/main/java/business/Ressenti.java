package business;

import javax.persistence.*;

@Entity
@Table(name="RESSENTIS")
public enum Ressenti {
    Interessant,
    Accessible,
    Complique,
    Fatiguant,
    Confus,
    Indifferent
}
