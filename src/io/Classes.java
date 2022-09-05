package io;

public enum Classes{

            ZERO(0,'0',"0"),
    ONE(1,'1',"1"),
    TWO(2,'2', "2"),
    THREE(3,'3', "3"),
    FOUR(4,'4',"4"),
    FIVE(5,'5', "5"),
    SIX(6,'6',"6"),
    SEVEN(7,'7', "7"),
    EIGHT(8,'8', "8"),
    NINE(9,'9',"9"),
    A      (10,'A',"A"      ),
    A_LOWER(11,'a',"A_LOWER"),
    B      (12,'B',"B"      ),
    B_LOWER(13,'b',"B_LOWER"),
    C      (14,'C',"C"      ),
    C_LOWER(15,'c',"C_LOWER"),
    D      (16,'D',"D"      ),
    D_LOWER(17,'d',"D_LOWER"),
    E      (18,'E',"E"      ),
    E_LOWER(19,'e',"E_LOWER"),
    F      (20,'F',"F"      ),
    F_LOWER(21,'f',"F_LOWER"),
    G      (22,'G',"G"      ),
    G_LOWER(23,'g',"G_LOWER"),
    H      (24,'H',"H"      ),
    H_LOWER(25,'h',"H_LOWER"),
    I      (26,'I',"I"      ),
    I_LOWER(27,'i',"I_LOWER"),
    J      (28,'J',"J"      ),
    J_LOWER(29,'j',"J_LOWER"),
    K      (30,'K',"K"      ),
    K_LOWER(31,'k',"K_LOWER"),
    L      (32,'L',"L"      ),
    L_LOWER(33,'l',"L_LOWER"),
    M      (34,'M',"M"      ),
    M_LOWER(35,'m',"M_LOWER"),
    N      (36,'N',"N"      ),
    N_LOWER(37,'n',"N_LOWER"),
    O      (38,'O',"O"      ),
    O_LOWER(39,'o',"O_LOWER"),
    P      (40,'P',"P"      ),
    P_LOWER(41,'p',"P_LOWER"),
    Q      (42,'Q',"Q"      ),
    Q_LOWER(43,'q',"Q_LOWER"),
    R      (44,'R',"R"      ),
    R_LOWER(45,'r',"R_LOWER"),
    S      (46,'S',"S"      ),
    S_LOWER(47,'s',"S_LOWER"),
    T      (48,'T',"T"      ),
    T_LOWER(49,'t',"T_LOWER"),
    U      (50,'U',"U"      ),
    U_LOWER(51,'u',"U_LOWER"),
    V      (52,'V',"V"      ),
    V_LOWER(53,'v',"V_LOWER"),
    W      (54,'W',"W"      ),
    W_LOWER(55,'w',"W_LOWER"),
    X      (56,'X',"X"      ),
    X_LOWER(57,'x',"X_LOWER"),
    Y      (58,'Y',"Y"      ),
    Y_LOWER(59,'y',"Y_LOWER"),
    Z      (60,'Z',"Z"      ),
    Z_LOWER(61,'z',"Z_LOWER");

    int index;
    char meaning;
    String folder;

    Classes(int index, char meaning, String folder) {
        this.index = index;
        this.meaning = meaning;
        this.folder = folder;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "index=" + index +
                ", meaning=" + meaning +
                '}';
    }

    public int getIndex() {
        return index;
    }

    public char getMeaning() {
        return meaning;
    }

    public String getFolder() {
        return folder;
    }


}