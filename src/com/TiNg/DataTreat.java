package com.TiNg;

public class DataTreat {
    public int[] tenToBinary(int i) {
        String s = Integer.toBinaryString(i);
        String s1 = s.substring(s.length() - 16, s.length());
        String s2 = s.substring(0, s.length() - 16);
        int i1 = Integer.valueOf(s1, 2);
        int i2 = Integer.valueOf(s2, 2);
        int[] i3 = {i1, i2};
        return i3;
    }

    public int readtenToBinary(int[] i) {
        String s = Integer.toBinaryString(i[0]);
        while (s.length() < 16) {
            s = "0" + s;
        }
        String s1 = Integer.toBinaryString(i[1]);
        String s2 = s1 + s;
        int i1 = Integer.valueOf(s2, 2);
        return i1;
    }

    public float readtenToBinarytoMM(int[] i, NewJPanel newJPanel) {
        String s = Integer.toBinaryString(i[0]);
        while (s.length() < 16) {
            s = "0" + s;
        }
        String s1 = Integer.toBinaryString(i[1]);
        String s2 = s1 + s;
        int i2 = Integer.valueOf(s2, 2);
        float i4 = (float) i2;
        float i3 = (i4 * 5 / newJPanel.getwulisubi() / newJPanel.getbujinxifen());
        return i3;
    }
}
