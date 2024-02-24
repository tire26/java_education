package ru.aston;

import java.util.Comparator;

public class Main {
    public static void main(String[] args) {

        example1();
        example2();
    }

    private static void example1() {
        CustomArray<Integer> customArray = new CustomArrayImpl<>();
        customArray.add(0, 2);
        customArray.add(1, 3);
        customArray.add(2, 4);
        customArray.add(0, 5);

        Comparator<Integer> comparator = (o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            } else if (o1 > o2) {
                return 1;
            } else {
                return -1;
            }
        };
        customArray.sort(comparator);
    }

    private static void example2() {
        CustomArray<AbraKadabra> abraKadabraCustomArray = new CustomArrayImpl<>();
        abraKadabraCustomArray.add(0, new AbraKadabra(2000));
        abraKadabraCustomArray.add(1, new AbraKadabra(2));
        abraKadabraCustomArray.add(2, new AbraKadabra(30000000));
        abraKadabraCustomArray.add(1, new AbraKadabra(11));
        Comparator<AbraKadabra> comparator = new Comparator<AbraKadabra>() {
            @Override
            public int compare(AbraKadabra o1, AbraKadabra o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else if (o1.getSpellValue() > o2.getSpellValue()) {
                    return 1;
                } else {
                    return -1;
                }
            }
        };
        abraKadabraCustomArray.sort(comparator);
    }

    private static class AbraKadabra {
        private int spellValue;

        public AbraKadabra(int spellValue) {
            this.spellValue = spellValue;
        }

        public int getSpellValue() {
            return spellValue;
        }
    }
}
