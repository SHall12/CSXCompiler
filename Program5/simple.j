class sample
.super java/lang/Object
.method public <init>()V
    .limit stack 1
    .limit locals 1
    aload_0
    invokespecial java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 3
    .limit locals 2

    iconst_4
    newarray double     ; Create 10 element array of doubles
    astore_1            ; store the reference into x

    aload_ 1    ; Array reference
    iconst_0    ; index
    ldc2_w 75.7 ; value to store
    iastore     ; store value in location

    aload_ 1    ; Array reference
    iconst_1    ; index
    ldc2_w 13   ; value to store
    iastore     ; store value in location

    aload_ 1    ; Array reference
    iconst_2    ; index
    ldc2_w 83.5 ; value to store
    iastore     ; store value in location

    aload_ 1    ; Array reference
    iconst_3    ; index
    ldc2_w -99   ; value to store
    iastore     ; store value in location

    ;invokevirtual java/io/PrintStream/println(I)V   ; print x
    return      ; return from main
.end method

.methid public static computeMax([D)V
    .limit stack
    .limit locals
    ; 0 stores array of scores
    ; 1 stores index of current score
    ; 2 stores max score
    ; 4 stores current score

    ; Store 0 to local variable, stores index
    iconst_0
    istore_1

    ; Set max to first element
    aload_0
    iload_1     ; current index
    daload
    dstore_2

    ; Store first element as current
    aload_0
    iload_1         ; load index
    daload          ; load array element
    dstore 4

    ; check if current score != -99
    dload 4
    ldc2_w -99      ; load constant -99
    dcmpg           ; compare current score to -99
    ifeq            ;  If matched -99, skip loop, NEEDS OFFSET = after loop
        ; Set new max
        dload 4     ; load current score
        dload_2     ; load max
        dcmpg       ; compare current score to max
        ifgt        ; check if element > max, NEEDS OFFSET = after next statment
            dload 4            ; set max to element
            dstore_2

        ; Increment index
        iload_1
        iconst_1
        iadd
        istore_1

        ; Update current score
        aload_0
        iload_1         ; load index
        daload          ; load array element
        dstore 4            ; update current score

        ; Jump back to loop
        goto        ; go back to before loop, NEEDS OFFSET = Loop

    ; Print max score

.end method

static public void computeMin(double[] scores)
