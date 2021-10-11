import java.util.ArrayList;
import java.util.*;

import static java.lang.System.exit;


class Vaccine {
    private String name;
    private int no_of_doses;
    private int gap;

    public int getGap() {
        return gap;
    }

    public Vaccine(String name, int no_of_doses, int gap) {
        // System.out.println(name);
        this.name = name;
        this.no_of_doses = no_of_doses;
        this.gap = gap;
    }

    public String getName() {
        return name;
    }

    public void details() {
        System.out.println("Vaccine Name : " + name + ", Number of Doses: " + no_of_doses + ", Gap Between Doses: " + gap);
    }

    public int getNo_of_doses() {
        return no_of_doses;
    }
}

class Slots {
    private Vaccine v;
    private int day;
    private int quantity;
    private Hospital H;

    public Vaccine getV() {
        return v;
    }

    public int getDay() {
        return day;
    }

    public int getQuantity() {
        return quantity;
    }

    public Hospital getH() {
        return H;
    }

    public Slots(Vaccine v, int day, int quantity, Hospital h) {
        this.v = v;
        this.day = day;
        this.quantity = quantity;
        H = h;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class Hospital {
    private String name;
    private int pincode;
    private int ID;
    private ArrayList<Slots> slots;

    public String getName() {
        return name;
    }

    public int getPincode() {
        return pincode;
    }

    public int getID() {
        return ID;
    }

    public Hospital(String name, int pincode, int index) {
        this.name = name;
        this.pincode = pincode;
        this.ID = 100000 + index;
        slots = new ArrayList<>();
    }

    public void details() {
        System.out.println("Hospital Name: " + name + ", PinCode: " + pincode + ", Unique ID: " + ID);
    }

    public void add_slots(Slots s) {
        slots.add(s);
    }

    public ArrayList<Slots> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<Slots> slots) {
        this.slots = slots;
    }

    public void book_Slot(int i) {
        slots.get(i).setQuantity(slots.get(i).getQuantity() - 1);
        if (slots.get(i).getQuantity() == 0) {
            slots.remove(i);
        }
    }

    public boolean check_vaccines(String vname) {
        for (int i = 0; i < slots.size(); i++) {
            if ( vname.equals(slots.get(i).getV().getName()) ) {
                return true;}
        }
        return false;
    }

    public boolean checkPincode(int pc, int nextDay) {
        for (int i = 0; i < slots.size(); i++) {
            if (nextDay != -1 && pc == pincode && nextDay == slots.get(i).getDay()) {
                return true;
            } else if (nextDay == -1 && pc == pincode) {
                return true;
            }
        }
        return false;
    }
}

class Citizen {
    private String name;
    private int age;
    private String ID;
    private String status;
    private int doses_delivered;
    private int first_day;
    private int next_day;
    private String Vaccine;

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public String getStatus() {
        return status;
    }

    public int getNext_day() {
        return next_day;
    }

    public int getDoses_delivered() {
        return doses_delivered;
    }

    public int getFirst_day() {
        return first_day;
    }

    public Citizen(String name, int age, String ID) {
        this.name = name;
        this.age = age;
        this.ID = ID;
        this.status = "Registered";
        doses_delivered = 0;
        first_day = 0;
        next_day = 0;
    }

    public void details() {
        System.out.println("Citizen Name: " + name + ", Age: " + age + ", Unique ID: " + ID);
        if (age < 18) {
            System.out.println("Only above 18 are allowed");
        }
    }

    public void print_status() {
        //System.out.println("react");
        if (status == "Registered") {
            System.out.println("Citizen " + status);
        } else if (status == "Partially Vaccinated") {
            System.out.println("Partially Vaccinated");
            System.out.println("Vaccine give: " + Vaccine);
            System.out.println("No of Doses given: " + doses_delivered);
            System.out.println("Next dose due date " + next_day);
        } else {
            System.out.println("Fully Vaccinated");
            System.out.println("Vaccine given: " + Vaccine);
            System.out.println("No of Doses given: " + doses_delivered);
        }

    }

    public void update(Vaccine V, int day) {
        Vaccine = V.getName();
        if (status == "Registered") {
            doses_delivered = 1;
            first_day = day;
            if (V.getGap() == 0) {
                status = "Fully Vaccinated";
                return;
            }
            status = "Partially Vaccinated";
            next_day = first_day + V.getGap();
            first_day = next_day;

        } else if (status == "Partially Vaccinated") {
            doses_delivered++;
            if (doses_delivered < V.getNo_of_doses()) {
                next_day = first_day + V.getGap();
                first_day = next_day;
            } else {
                status = "Fully Vaccinated";
            }
        }
    }
}


public class CovinPortal {
    public static void display_Menu() {
        System.out.println("---------------------------------------\n" +
                "1. Add Vaccine\n" +
                "2. Register Hospital\n" +
                "3. Register Citizen\n" +
                "4. Add Slot for Vaccination\n" +
                "5. Book Slot for Vaccination\n" +
                "6. List all slots for a hospital\n" +
                "7. Check Vaccination Status\n" +
                "8. Exit\n" + "---------------------------------------\n" + "Make a choice(1 - 8)");


    }

    public static void main(String[] args) {
        System.out.println("CoWin Portal initialized..... ");
        Scanner sc = new Scanner(System.in);
        ArrayList<Vaccine> vaccines = new ArrayList<>();
        ArrayList<Hospital> hospital = new ArrayList<>();
        ArrayList<Citizen> citizens = new ArrayList<>();

        while (true) {
            display_Menu();
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Vaccine Name: ");
                    String name = sc.nextLine();
                    System.out.print("Number of Doses: ");
                    int doses = sc.nextInt();
                    int gap = 0;
                    if (doses > 1) {
                        System.out.print("Gap between Doses: ");
                        gap = sc.nextInt();
                    }
                    Vaccine v = new Vaccine(name, doses, gap);
                    vaccines.add(v);
                    v.details();
                    break;
                case 2:
                    System.out.print("Hospital Name: ");
                    String naam = sc.nextLine();
                    System.out.print("PinCode: ");
                    int pincode = sc.nextInt();
                    Hospital h = new Hospital(naam, pincode, hospital.size());
                    hospital.add(h);
                    h.details();
                    break;
                case 3:
                    System.out.print("Citizen Name: ");
                    String namee = sc.nextLine();
                    System.out.print("Age: ");
                    int umar = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Unique ID: ");
                    String Id = sc.nextLine();
                    Citizen c = new Citizen(namee, umar, Id);
                    if (umar > 18) {
                        citizens.add(c);
                    }
                    c.details();
                    break;
                case 4:
                    System.out.print("Enter Hospital ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter number of Slots to be add: ");
                    int nslot = sc.nextInt();
                    while (nslot-- > 0) {
                        System.out.print("Enter Day Number: ");
                        int dn = sc.nextInt();
                        System.out.print("Enter Quantity: ");
                        int quan = sc.nextInt();
                        System.out.println("Select Vaccine");
                        for (int i = 0; i < vaccines.size(); i++) {
                            System.out.println(i + "." + vaccines.get(i).getName());
                        }
                        int vaci_option = sc.nextInt();
                        for (int i = 0; i < hospital.size(); i++) {
                            if (id == hospital.get(i).getID()) {
                                System.out.println("Slot added by Hospital " + id + " for Day: " + dn + ", Available Quantity: " + quan + " of Vaccine " + vaccines.get(vaci_option).getName());
                                Slots sl = new Slots(vaccines.get(vaci_option), dn, quan, hospital.get(i));
                                hospital.get(i).add_slots(sl);
                                break;
                            }
                        }
                    }
                    break;
                case 5:
                    System.out.print("Enter patient Unique ID: ");
                    String patient_id = sc.nextLine();
                    System.out.println("1. Search by area\n" +
                            "2. Search by Vaccine\n" +
                            "3. Exit");
                    int option = sc.nextInt();
                    sc.nextLine();

                    int ci = 0;
                    for (int i = 0; i < citizens.size(); i++) {
                        if (citizens.get(i).getID().equals(patient_id)) {
                            ci = i;
                            break;
                        }
                    }

                    Citizen cit = citizens.get(ci);

                    if (option == 1) {
                        System.out.println("Enter the PinCode : ");
                        int pinc = sc.nextInt();
                        boolean not_found = true;

                        for (int i = 0; i < hospital.size(); i++) {

                            if (pinc == hospital.get(i).getPincode()) {
                                System.out.println(hospital.get(i).getID() + " " + hospital.get(i).getName());
                                not_found = false;
                            }
                        }
                        if (not_found == true) {
                            System.out.println("No hospital in this area");
                        } else {
                            System.out.println("Enter hospital ID: ");
                            int ID = sc.nextInt();
                            int in = 0;
                            for (int i = 0; i < hospital.size(); i++) {
                                if (ID == hospital.get(i).getID()) {
                                    in = i;
                                    break;
                                }
                            }
                            Hospital hos = hospital.get(in);
                            boolean no_slots = true;
                            for (int i = 0; i < hos.getSlots().size(); i++) {
                                Slots slot = hos.getSlots().get(i);
                                if (cit.getStatus().equals("Partially Vaccinated") && cit.getNext_day() == slot.getDay()) {
                                    System.out.println(i + "->Day: " + slot.getDay() + " Available Qty: " + slot.getQuantity() + " Vaccine: " + slot.getV().getName());
                                    no_slots = false;
                                } else if (!cit.getStatus().equals("Partially Vaccinated")) {
                                    System.out.println(i + "->Day: " + slot.getDay() + " Available Qty: " + slot.getQuantity() + " Vaccine: " + slot.getV().getName());
                                    no_slots = false;
                                }
                            }

                            if (no_slots) {
                                System.out.println("No slot is available");

                            } else {

                                System.out.println("Choose Slot: ");
                                ;
                                int cs = sc.nextInt();
                                hos.book_Slot(cs);

                                //citizen

                                cit.update(hos.getSlots().get(cs).getV(), hos.getSlots().get(cs).getDay());
                                System.out.println(cit.getName() + " vaccinated with " + hos.getSlots().get(cs).getV().getName());
                            }
                        }

                    } else if (option == 2) {
                        System.out.println("Enter vaccine Name: ");
                        String vname = sc.nextLine();
                        boolean not_found = true;
                        for (int i = 0; i < hospital.size(); i++) {
                          

                            if (hospital.get(i).check_vaccines(vname)) {

                                not_found = false;

                                System.out.println(hospital.get(i).getID() + " " + hospital.get(i).getName());
                            }

                        }
                        if (not_found) {
                            System.out.println("No vaccine Found");
                        } else {
                            System.out.println("Enter hospital ID: ");
                            int ID = sc.nextInt();
                            int in = 0;
                            for (int i = 0; i < hospital.size(); i++) {
                                if (ID == hospital.get(i).getID()) {
                                    in = i;
                                    break;
                                }
                            }
                            Hospital hos = hospital.get(in);

                            boolean no_slots = true;
                            for (int i = 0; i < hos.getSlots().size(); i++) {
                                Slots slot = hos.getSlots().get(i);
                                if (cit.getStatus().equals("Partially Vaccinated") && cit.getNext_day() == slot.getDay()) {
                                    System.out.println(i + "->Day: " + slot.getDay() + " Available Qty: " + slot.getQuantity() + " Vaccine: " + slot.getV().getName());
                                    no_slots = false;
                                } else if (!cit.getStatus().equals("Partially Vaccinated")) {
                                    //idhar hamne badlaao kiye
                                    if (vname.equals(slot.getV().getName())) {
                                        System.out.println(i + "->Day: " + slot.getDay() + " Available Qty: " + slot.getQuantity() + " Vaccine: " + slot.getV().getName());
                                        no_slots = false;
                                    }

                                }
                            }

                            if (no_slots) System.out.println("no slots available");
                            else {
                                System.out.println("Choose Slot: ");

                                int cs = sc.nextInt();
                                hos.book_Slot(cs);

                                //citizen
                                cit.update(hos.getSlots().get(cs).getV(), hos.getSlots().get(cs).getDay());
                                System.out.println(cit.getName() + " vaccinated with " + hos.getSlots().get(cs).getV().getName());
                            }

                        }
                    }
                    break;
                case 6:
                    System.out.println("Enter hospital ID: ");
                    int ID = sc.nextInt();
                    int in = 0;
                    for (int i = 0; i < hospital.size(); i++) {
                        if (ID == hospital.get(i).getID()) {
                            in = i;
                            break;
                        }
                    }
                    Hospital hos = hospital.get(in);

                    for (int i = 0; i < hos.getSlots().size(); i++) {
                        Slots slot = hos.getSlots().get(i);
                        System.out.println("Day: " + slot.getDay() + " Available Qty: " + slot.getQuantity() + " Vaccine: " + slot.getV().getName());
                    }
                    break;
                case 7:
                    System.out.print("Enter Patient ID: ");

                    String patient_Id = sc.nextLine();
                    for (int i = 0; i < citizens.size(); i++) {
                        //System.out.println(citizens.get(i).getID());
                        if (patient_Id.equals(citizens.get(i).getID())) {
                            citizens.get(i).print_status();
                        }


                    }
                    break;
                case 8:
                    exit(1);
                default:
                    break;
            }
        }
    }
}

