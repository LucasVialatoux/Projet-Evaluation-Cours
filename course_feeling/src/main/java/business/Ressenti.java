package business;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "RESSENTIS")
public enum Ressenti {
    Interessant,
    Accessible,
    Complique,
    Monotone,
    Confus,
    Effraye
}
