import java.util.Scanner;

class Empleado {
    String nombreCompleto;
    int identificador;

    Empleado(String nombreCompleto, int identificador) {
        this.nombreCompleto = nombreCompleto;
        this.identificador = identificador;
    }
}

class Departamento {
    String nombre;
    String ubicacion;
    Empleado[] empleados = new Empleado[10];
    int numEmpleados = 0;

    Departamento(String nombre, String ubicacion) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
    }

    void agregarEmpleado(Empleado e) {
        if (numEmpleados < empleados.length) {
            empleados[numEmpleados] = e;
            numEmpleados++;
        }
    }

    Empleado buscarEmpleado(int id) {
        for (int i = 0; i < numEmpleados; i++) {
            if (empleados[i].identificador == id) {
                return empleados[i];
            }
        }
        return null;
    }

    void mostrarEmpleados() {
        if (numEmpleados == 0) {
            System.out.println("Este departamento no tiene empleados.");
            return;
        }
        for (int i = 0; i < numEmpleados; i++) {
            System.out.println(empleados[i].identificador + " - " + empleados[i].nombreCompleto);
        }
    }
}

class EncargadoRH {
    Departamento[] departamentos;

    EncargadoRH(Departamento[] departamentos) {
        this.departamentos = departamentos;
    }

    Departamento buscarDepartamento(int opcion) {
        if (opcion < 1 || opcion > departamentos.length) {
            return null;
        }
        return departamentos[opcion - 1];
    }

    Empleado consultarEmpleado(Departamento d, int id) {
        return d.buscarEmpleado(id);
    }
}

class MenuRH {
    static final int NUM_DEPARTAMENTOS = 5;

    private final EncargadoRH rh;
    private final Departamento[] departamentos;
    private final Scanner sc;

    MenuRH(EncargadoRH rh, Departamento[] departamentos, Scanner sc) {
        this.rh = rh;
        this.departamentos = departamentos;
        this.sc = sc;
    }

    void ejecutar() {
        String seguir = "s";

        while (seguir.equalsIgnoreCase("s")) {
            mostrarDepartamentos();
            System.out.print("Seleccione un departamento (1-" + NUM_DEPARTAMENTOS + "): ");
            Departamento d = rh.buscarDepartamento(leerEntero());

            if (d == null) {
                System.out.println("Departamento no valido.");
            } else {
                atenderDepartamento(d);
            }

            System.out.print("¿Hacer otra consulta? (s/n): ");
            seguir = sc.next();
        }
    }

    private void mostrarDepartamentos() {
        System.out.println("Departamentos:");
        for (int i = 0; i < departamentos.length; i++) {
            System.out.println((i + 1) + ". " + departamentos[i].nombre + " (" + departamentos[i].ubicacion + ")");
        }
    }

    private void atenderDepartamento(Departamento d) {
        System.out.println("1. Consultar empleado por identificador");
        System.out.println("2. Ver todos los empleados del departamento");
        System.out.print("Seleccione una opcion (1-2): ");
        int accion = leerEntero();

        if (accion == 1) {
            consultarPorIdentificador(d);
        } else if (accion == 2) {
            System.out.println("Empleados de " + d.nombre + ":");
            d.mostrarEmpleados();
        } else {
            System.out.println("Opcion no valida.");
        }
    }

    private void consultarPorIdentificador(Departamento d) {
        System.out.print("Identificador del empleado (5 digitos): ");
        int id = leerEntero();
        Empleado e = rh.consultarEmpleado(d, id);

        if (e == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }
        System.out.println("Nombre: " + e.nombreCompleto);
        System.out.println("Identificador: " + e.identificador);
        System.out.println("Departamento: " + d.nombre);
        System.out.println("Ubicacion: " + d.ubicacion);
    }

    private int leerEntero() {
        while (!sc.hasNextInt()) {
            sc.next();
            System.out.print("Escriba un numero valido: ");
        }
        return sc.nextInt();
    }
}

public class Main {
    static Departamento[] crearDepartamentos() {
        Departamento[] departamentos = new Departamento[MenuRH.NUM_DEPARTAMENTOS];
        departamentos[0] = new Departamento("Ventas", "Oficina central");
        departamentos[1] = new Departamento("Finanzas", "Sucursal");
        departamentos[2] = new Departamento("Sistemas", "Oficina central");
        departamentos[3] = new Departamento("Compras", "Sucursal");
        departamentos[4] = new Departamento("Logistica", "Sucursal");
        return departamentos;
    }

    static void cargarEmpleadosDePrueba(Departamento[] departamentos) {
        departamentos[0].agregarEmpleado(new Empleado("Ana Lopez", 10001));
        departamentos[0].agregarEmpleado(new Empleado("Luis Perez", 10002));
        departamentos[2].agregarEmpleado(new Empleado("Carlos Ruiz", 30001));
    }

    public static void main(String[] args) {
        Departamento[] departamentos = crearDepartamentos();
        cargarEmpleadosDePrueba(departamentos);

        EncargadoRH rh = new EncargadoRH(departamentos);
        Scanner sc = new Scanner(System.in);

        new MenuRH(rh, departamentos, sc).ejecutar();

        sc.close();
    }
}