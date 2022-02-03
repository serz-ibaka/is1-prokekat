package jparesources;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import jparesources.Isplata;
import jparesources.Komitent;
import jparesources.Mesto;
import jparesources.Prenos;
import jparesources.Transakcija;
import jparesources.Uplata;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-03T03:57:24")
@StaticMetamodel(Racun.class)
public class Racun_ { 

    public static volatile ListAttribute<Racun, Isplata> isplataList;
    public static volatile SingularAttribute<Racun, Float> stanje;
    public static volatile SingularAttribute<Racun, Date> otvaranje;
    public static volatile SingularAttribute<Racun, Float> dozvoljeniminus;
    public static volatile ListAttribute<Racun, Prenos> prenosList;
    public static volatile SingularAttribute<Racun, Integer> brojtransakcija;
    public static volatile SingularAttribute<Racun, Integer> idrac;
    public static volatile ListAttribute<Racun, Prenos> prenosList1;
    public static volatile ListAttribute<Racun, Uplata> uplataList;
    public static volatile SingularAttribute<Racun, Mesto> idmes;
    public static volatile SingularAttribute<Racun, Komitent> idkom;
    public static volatile ListAttribute<Racun, Transakcija> transakcijaList;
    public static volatile SingularAttribute<Racun, Character> status;

}