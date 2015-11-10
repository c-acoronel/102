package com.copnaf.linea102.domain;

import com.openpojo.random.RandomFactory;
import com.openpojo.random.RandomGenerator;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoClassFilter;
import com.openpojo.reflection.PojoField;
import com.openpojo.reflection.PojoPackage;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.PojoValidator;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.Rule;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoNestedClassRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.DefaultValuesNullTester;
import com.openpojo.validation.utils.IdentityHandlerStub;
import com.openpojo.validation.utils.ValidationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * @author Andres
 */
public class DomainObjectsTest {

    private static final Logger LOG = LoggerFactory
            .getLogger(DomainObjectsTest.class);

    // The package to test
    private static final String POJO_PACKAGE = "com.copnaf.linea102.domain";

    private List<PojoClass> pojoClasses;
    private PojoValidator pojoValidator;

    @BeforeMethod
    public void setup() {

        RandomFactory.addRandomGenerator(XMLGregorianCalendarRandomGenerator.getInstance());

        pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE,
                new NonTestClassPackageFilter());

        pojoValidator = new PojoValidator();

        // Create Rules to validate structure for POJO_PACKAGE
        pojoValidator.addRule(new NoPublicNonStaticFinalFieldsRule());
        pojoValidator.addRule(new NoStaticExceptFinalRule());
        pojoValidator.addRule(new GetterMustExistRule());
        pojoValidator.addRule(new NoNestedClassRule());

        // Create Testers to validate behaviour for POJO_PACKAGE
        pojoValidator.addTester(new DefaultValuesNullTester());
        pojoValidator.addTester(new GetterTester());
        pojoValidator.addTester(new SetterTester());
        pojoValidator.addTester(new EqualsTester());
        pojoValidator.addTester(new HashCodeTester());
    }

    @Test
    public void testPojoStructureAndBehavior() {
        LOG.info("Testing [{}] classes", pojoClasses.size());
        for (PojoClass pojoClass : pojoClasses)
            pojoValidator.runValidation(pojoClass);
    }

    private static class NonTestClassPackageFilter implements PojoClassFilter {

        //        @Override
        public boolean include(PojoClass pojoClass) {
            return !pojoClass.isEnum()
                    && !pojoClass.getName().endsWith(
                    PojoPackage.PACKAGE_DELIMETER
                            + PojoPackage.PACKAGE_INFO)
                    && !pojoClass.getName().contains("Test");
        }
    }

    private static class NoPublicNonStaticFinalFieldsRule implements Rule {
        //        @Override
        public void evaluate(PojoClass pojoClass) {
            for (PojoField fieldEntry : pojoClass.getPojoFields())
                if (fieldEntry.isPublic() && !fieldEntry.isStatic()
                        && !fieldEntry.isFinal())
                    Affirm.fail(String.format("Public fields=[%s] not allowed",
                            fieldEntry));
        }
    }

    private static class EqualsTester implements Tester {
        //        @Override
        public void run(PojoClass pojoClass) {
            final Object firstClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            final Object secondClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            Affirm.affirmTrue(String.format("[%s] is not equal to [%s]",
                            firstClassInstance, secondClassInstance),
                    firstClassInstance.equals(secondClassInstance)
            );

            Affirm.affirmFalse(String.format("Expected false for [%s] equals null", firstClassInstance),
                    firstClassInstance.equals(null)
            );

            Affirm.affirmTrue(String.format("Expected true for [%s] equals this", firstClassInstance),
                    firstClassInstance.equals(firstClassInstance)
            );

            final Object dummyObject = new Object();
            Affirm.affirmFalse(String.format("Expected false for [%s] equals [%s]", firstClassInstance, dummyObject),
                    firstClassInstance.equals(dummyObject)
            );
        }
    }

    private static class HashCodeTester implements Tester {
        //        @Override
        public void run(PojoClass pojoClass) {
            final Object firstClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            final Object secondClassInstance = ValidationHelper.getBasicInstance(pojoClass);
            Affirm.affirmTrue(String.format("Hashcode of [%s] is not equal to hashcode of [%s]",
                            firstClassInstance.getClass(), secondClassInstance.getClass()),
                    firstClassInstance.hashCode() == secondClassInstance.hashCode()
            );
        }
    }

    public class GetterTester implements Tester {
        public void run(final PojoClass pojoClass) {
            final Object classInstance = ValidationHelper.getBasicInstance(pojoClass);
            for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
                if (!isExcluded(fieldEntry) && fieldEntry.hasGetter()) {
                    Object value = fieldEntry.get(classInstance);

                    if (!fieldEntry.isFinal() && fieldEntry.hasSetter()) {
                        value = RandomFactory.getRandomValue(fieldEntry.getType());
                        fieldEntry.set(classInstance, value);
                    }

                    IdentityHandlerStub.registerIdentityHandlerStubForValue(value);
                    Affirm.affirmEquals("Getter returned non equal value for field=[" + fieldEntry + "]", value,
                            fieldEntry.invokeGetter(classInstance));
                    IdentityHandlerStub.unregisterIdentityHandlerStubForValue(value);
                }
            }
        }

        private boolean isExcluded(PojoField fieldEntry) {
            return fieldEntry.getType().isEnum();
        }
    }

    public class SetterTester implements Tester {
        public void run(final PojoClass pojoClass) {
            final Object classInstance = ValidationHelper.getBasicInstance(pojoClass);
            for (final PojoField fieldEntry : pojoClass.getPojoFields()) {
                if (!isExcluded(fieldEntry) && fieldEntry.hasSetter()) {
                    Object value = fieldEntry.get(classInstance);

                    if (!fieldEntry.isFinal() && fieldEntry.hasSetter()) {
                        value = RandomFactory.getRandomValue(fieldEntry.getType());
                        fieldEntry.set(classInstance, value);
                    }

                    IdentityHandlerStub.registerIdentityHandlerStubForValue(value);
                    Affirm.affirmEquals("Setter test failed, non equal value for field=[" + fieldEntry + "]", value,
                            fieldEntry.get(classInstance));
                    IdentityHandlerStub.unregisterIdentityHandlerStubForValue(value);
                }
            }
        }

        private boolean isExcluded(PojoField fieldEntry) {
            return fieldEntry.getType().isEnum();
        }
    }


    private static class XMLGregorianCalendarRandomGenerator implements RandomGenerator {
        private XMLGregorianCalendarRandomGenerator() {
        }

        public static RandomGenerator getInstance() {
            return Instance.INSTANCE;
        }

        private static final Class<?>[] TYPES = new Class<?>[]{
                XMLGregorianCalendar.class
        };

        public Object doGenerate(final Class<?> type) {
            if (type == XMLGregorianCalendar.class) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                DatatypeFactory datatypeFactory = null;
                try {
                    datatypeFactory = DatatypeFactory.newInstance();
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();
                }
                return datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
            }
            return null;
        }

        public Collection<Class<?>> getTypes() {
            return Arrays.asList(TYPES);
        }

        private static class Instance {
            private static final RandomGenerator INSTANCE = new XMLGregorianCalendarRandomGenerator();
        }
    }
}
