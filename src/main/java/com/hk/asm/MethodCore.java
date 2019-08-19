package com.hk.asm;

import org.objectweb.asm.*;

public class MethodCore {


    //生成方法
    private byte[] generateInterface() {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC,
                "pkg/Bean", null, "java/lang/Object",
                new String[] {});

        {
            MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getF", "()I", null
                    , null);
            mv.visitCode();
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitFieldInsn(Opcodes.GETFIELD, "pkb/Bean", "f", "I");
            mv.visitInsn(Opcodes.IRETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }

        MethodVisitor setFMv = cw.visitMethod(Opcodes.ACC_PUBLIC, "checkAndSetF", "(I)V", null, null);
        setFMv.visitCode();
        setFMv.visitVarInsn(Opcodes.ILOAD, 1);
        Label label = new Label();
        setFMv.visitJumpInsn(Opcodes.IFLT, label);
        setFMv.visitVarInsn(Opcodes.ALOAD, 0);
        setFMv.visitVarInsn(Opcodes.ILOAD, 1);
        setFMv.visitFieldInsn(Opcodes.PUTFIELD, "pkg/Bean", "f", "I");
        Label end = new Label();
        setFMv.visitJumpInsn(Opcodes.GOTO, end);
        setFMv.visitLabel(label);
        setFMv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        setFMv.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalArgumentException");
        setFMv.visitInsn(Opcodes.DUP);
        setFMv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V", false);
        setFMv.visitInsn(Opcodes.ATHROW);
        setFMv.visitLabel(end);
        setFMv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        setFMv.visitInsn(Opcodes.RETURN);
        setFMv.visitMaxs(2, 2);
        setFMv.visitEnd();
        return cw.toByteArray();
    }

    //转化方法，移除NOP指令
    public class RemoveNopAdapter extends MethodVisitor {

        public RemoveNopAdapter(MethodVisitor mv) {
            super(Opcodes.ASM4, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            if(opcode != Opcodes.NOP) {
                mv.visitInsn(opcode);
            }
        }

    }

    //转化方法，插入类适配器
    public class RemoveNopClassAdapter extends ClassVisitor {

        public RemoveNopClassAdapter(ClassVisitor cv) {
            super(Opcodes.ASM4, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor mv;
            mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            if (mv != null) {
                mv = new RemoveNopAdapter(mv);
            }
            return mv;
        }


    }


    public class AddTimerAdapter extends ClassVisitor {
        private String owner;
        private boolean isInterface;
        public AddTimerAdapter(ClassVisitor cv) {
            super(Opcodes.ASM4, cv);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            cv.visit(version, access, name, signature, superName, interfaces);
            owner = name;
            isInterface = (access & Opcodes.ACC_INTERFACE) != 0;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
            if(!isInterface && mv != null && !name.equals("<init>")) {
                mv = new AddTimerMethodAdapter(mv);
            }
            return  mv;
        }

        @Override
        public void visitEnd() {
            if(!isInterface) {
                FieldVisitor fv = cv.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "timer", "J", null, null);
                if(fv != null) {
                    fv.visitEnd();
                }
            }
            cv.visitEnd();
        }

        //添加定时器
        class AddTimerMethodAdapter extends MethodVisitor {
            public AddTimerMethodAdapter(MethodVisitor mv) {
                super(Opcodes.ASM4, mv);
            }

            @Override
            public void visitCode() {
                mv.visitCode();
                mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                mv.visitInsn(Opcodes.LSUB);
                mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
            }

            @Override
            public void visitInsn(int opcode) {
                if((opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN) || opcode == Opcodes.ATHROW) {
                    mv.visitFieldInsn(Opcodes.GETSTATIC, owner, "timer", "J");
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J", false);
                    mv.visitInsn(Opcodes.LADD);
                    mv.visitFieldInsn(Opcodes.PUTSTATIC, owner, "timer", "J");
                    mv.visitInsn(opcode);
                }
            }

            @Override
            public void visitMaxs(int maxStack, int maxLocals) {
                mv.visitMaxs(maxStack + 4, maxLocals);
            }
        }
    }




    public static void main(String[] args) {

    }
}
