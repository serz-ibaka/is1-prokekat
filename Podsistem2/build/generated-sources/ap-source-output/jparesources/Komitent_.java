package jparesources;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.Mesto;
import jparesources.Racun;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-02T23:20:17")
@StaticMetamodel(Komitent.class)
public class Komitent_ { 

    public static volatile SingularAttribute<Komitent, Mesto> idmes;
    public static volatile ListAttribute<Komitent, Racun> racunList;
    public static volatile SingularAttribute<Komitent, Integer> idkom;
    public static volatile SingularAttribute<Komitent, String> naziv;
    public static volatile SingularAttribute<Komitent, String> adresa;

}