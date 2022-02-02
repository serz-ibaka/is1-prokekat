package jparesources;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.Isplata;
import jparesources.Prenos;
import jparesources.Racun;
import jparesources.TransakcijaPK;
import jparesources.Uplata;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-01T21:27:24")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Isplata> isplata;
    public static volatile SingularAttribute<Transakcija, Float> iznos;
    public static volatile SingularAttribute<Transakcija, String> svrha;
    public static volatile SingularAttribute<Transakcija, Date> obavljanje;
    public static volatile SingularAttribute<Transakcija, Racun> racun;
    public static volatile SingularAttribute<Transakcija, Prenos> prenos;
    public static volatile SingularAttribute<Transakcija, TransakcijaPK> transakcijaPK;
    public static volatile SingularAttribute<Transakcija, Uplata> uplata;

}