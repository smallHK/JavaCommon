package com.hk.asm;

import org.objectweb.asm.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class Core {


    //解析class文件
    public class ClassPrinter extends ClassVisitor {

        public ClassPrinter() {
            super(Opcodes.ASM4);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            System.out.println(name + " extends " +superName + "{");
        }

        @Override
        public void visitSource(String source, String debug) {

        }

        @Override
        public void visitOuterClass(String owner, String name, String descriptor) {

        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            return null;
        }

        @Override
        public void visitAttribute(Attribute attribute) {

        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {

        }

        @Override
        public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
            System.out.println("    " + descriptor +"   ");
            return  null;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            System.out.println("    " + name + descriptor);
            return null;
        }

        @Override
        public void visitEnd() {
            System.out.println("}");
        }
    }

    private void parseClass() {
        ClassPrinter cp = new ClassPrinter();

        try {
            ClassReader cr = new ClassReader("java.lang.Runnable");
            cr.accept(cp, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //生成类
    //生成接口
    private byte[] generateInterface() {
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
        return b;
    }


    private class MyClassLoader extends ClassLoader {
        private Class defineClass(String name, byte[] b) {
            return defineClass(name, b, 0, b.length);
        }
    }

    private void useGeneratedInterface() {
        var cl = new MyClassLoader();
        Class c = cl.defineClass("com/hk/asm/Comparable", generateInterface());
    }

    private void transformClass() {


        byte[] b1 = new byte[0];
        try {
            b1 = Files.readAllBytes(Path.of(System.getProperty("user.dir"), "target", "classes",
                    "com", "hk", "App.class"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClassReader cr = new ClassReader(b1);
        ClassWriter cw = new ClassWriter(cr,0);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM4, cw) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(Opcodes.V1_5, access, name, signature, superName, interfaces);
            }
        };
        cr.accept(cv, 0);
        byte[] b2 = cw.toByteArray();

    }

    //转化class版本
    public class ChangeVersionAdapter extends ClassVisitor {
        public ChangeVersionAdapter(ClassVisitor cv) {
            super(Opcodes.ASM4, cv);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            cv.visit(Opcodes.V1_5, access, name, signature, superName, interfaces);
        }
    }


    //移除类外部类、内部类以及被编译类的源文件名
    public class RemoveDebugAdapter extends ClassVisitor {

        public RemoveDebugAdapter(ClassVisitor cv) {
            super(Opcodes.ASM4, cv);
        }

        @Override
        public void visitSource(String source, String debug) {

        }

        @Override
        public void visitOuterClass(String owner, String name, String descriptor) {

        }

        @Override
        public void visitInnerClass(String name, String outerName, String innerName, int access) {

        }
    }

    //移除方法
    public class RemoveMethodAdapter extends ClassVisitor {
        private String mName;
        private String mDesc;

        public RemoveMethodAdapter(ClassVisitor cv, String mName, String mDesc) {
            super(Opcodes.ASM4, cv);
            this.mName = mName;
            this.mDesc = mDesc;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if(name.equals(mName) && descriptor.equals(mDesc)) {
                return null;
            }
            return cv.visitMethod(access, name, descriptor, signature, exceptions);
        }
    }


    public static void main(String[] args) {

    }
}
