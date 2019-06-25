module service {
    requires java.base;
    exports java9.service;
//    requires web; // cyclic (순환참조 종속성문제)
}