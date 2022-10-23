package az.et.unitech.account.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static az.et.unitech.account.architecture.ArchConstants.*;
import static az.et.unitech.account.architecture.CommonRules.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

public class ControllerRules {

    @ArchTest
    static final ArchRule component_annotation_is_not_allowed = componentAnnotationIsNotAllowedRule(CONTROLLER_PKG);

    @ArchTest
    static final ArchRule classes_should_be_annotated_with_RestController = classes()
            .that().resideInAPackage(CONTROLLER_PKG)
            .should().beAnnotatedWith(RestController.class)
            .because(String.format(ANNOTATED_EXPLANATION, CONTROLLER_SUFFIX, "@RestController"));

    @ArchTest
    static final ArchRule classes_name_should_be_ending_with_controller =
            classesShouldBeNamedProperly(CONTROLLER_PKG, CONTROLLER_SUFFIX);

    @ArchTest
    static final ArchRule fields_should_not_be_public = fieldsShouldNotBePublicRule(CONTROLLER_PKG);

    @ArchTest
    static final ArchRule constructors_should_not_be_private = publicConstructorsRule(CONTROLLER_PKG);

    @ArchTest
    static final ArchRule bean_methods_are_not_allowed = beanMethodsAreNotAllowedRule(CONTROLLER_PKG);

    @ArchTest
    static final ArchRule private_methods_are_not_allowed = privateMethodsAreNotAllowedRule(CONTROLLER_PKG);

    @ArchTest
    static final ArchRule static_methods_are_not_allowed = staticMethodsAreNotAllowedRule(CONTROLLER_PKG);

    @ArchTest
    static final ArchRule methods_should_return_response_entity = methods()
            .that().arePublic()
            .and().areDeclaredInClassesThat().resideInAPackage(CONTROLLER_PKG)
            .should().haveRawReturnType(ResponseEntity.class)
            .because("Controller endpoints should return a ResponseEntity object");

    @ArchTest
    static final ArchRule methods_should_be_annotated_with_valid_annotations = methods()
            .that().arePublic()
            .and().areDeclaredInClassesThat().resideInAPackage(CONTROLLER_PKG)
            .should().beAnnotatedWith(PostMapping.class)
            .orShould().beAnnotatedWith(GetMapping.class)
            .orShould().beAnnotatedWith(DeleteMapping.class)
            .orShould().beAnnotatedWith(PatchMapping.class)
            .orShould().beAnnotatedWith(PutMapping.class)
            .because("Controller methods should be annotated only with valid options of "
                    + "REST (POST, PUT, PATCH, GET, and DELETE)");

    @ArchTest
    static final ArchRule no_classes_with_RestController_annotation_should_reside_outside_of_controller_packages =
            noClasses()
                    .that().areAnnotatedWith(RestController.class)
                    .should().resideOutsideOfPackage(CONTROLLER_PKG);

}
