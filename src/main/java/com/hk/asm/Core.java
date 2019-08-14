package com.hk.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;


public class Core {


    public void generateInterface() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT + Opcodes.ACC_INTERFACE,
                "com/hk/asm/Comparable", null, "java/lang/Object",
                new String[] {});
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "LESS", "I", null
                , -1).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC,"EQUAL", "I", null,
                0).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL + Opcodes.ACC_STATIC, "GREATER", "I", null,
                1).visitEnd();
        cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_ABSTRACT, "compareTo", "(Ljava/lang/Object;)I", null
                , null).visitEnd();

        byte[] b = cw.toByteArray();




    }

    public static void main(String[] args) {

    }
}
