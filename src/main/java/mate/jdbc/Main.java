package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.models.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc");

    public static void main(String[] args) {
        Manufacturer manufacturerBmw = new Manufacturer();
        manufacturerBmw.setName("BMW");
        manufacturerBmw.setCountry("Germany");
        Manufacturer manufacturerLexus = new Manufacturer();
        manufacturerLexus.setName("Lexus");
        manufacturerLexus.setCountry("Japan");
        Manufacturer manufacturerVolkswagen = new Manufacturer();
        manufacturerVolkswagen.setName("Volkswagen");
        manufacturerVolkswagen.setCountry("German");
        ManufacturerDao manufacturerDao = (ManufacturerDao) injector.getInstance(
                ManufacturerDao.class);
        Manufacturer manufacturer1 = manufacturerDao.create(manufacturerBmw);
        Manufacturer manufacturer2 = manufacturerDao.create(manufacturerLexus);
        Manufacturer manufacturer3 = manufacturerDao.create(manufacturerVolkswagen);
        System.out.println(manufacturer1);
        System.out.println(manufacturer2);
        System.out.println(manufacturer3);

        manufacturerDao.getAll().forEach(System.out::println);

        System.out.println(manufacturerDao.delete(1L));

        manufacturerBmw.setName("Audi");
        Manufacturer manufacturerBmwUpdate = manufacturerDao.update(manufacturerBmw);
        System.out.println(manufacturerBmwUpdate);

        Optional<Manufacturer> manufacturerOptional = manufacturerDao.get(2L);
        System.out.println(manufacturerOptional);
    }
}
