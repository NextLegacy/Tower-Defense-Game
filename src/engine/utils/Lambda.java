package engine.utils;

import java.util.ArrayList;
import java.util.HashMap;

public final class Lambda 
{
    private Lambda() { }

    public abstract interface IAction { }

    @FunctionalInterface public interface Action extends IAction
    { public void invoke(Object... args); }

    @FunctionalInterface public interface Action0 extends IAction 
    { public void invoke(); }
    
    @FunctionalInterface public interface Action1<T1> extends IAction 
    { public void invoke(T1 arg1); }
    
    @FunctionalInterface public interface Action2<T1, T2> extends IAction 
    { public void invoke(T1 arg1, T2 arg2); }
    
    @FunctionalInterface public interface Action3<T1, T2, T3> extends IAction 
    { public void invoke(T1 arg1, T2 arg2, T3 arg3); }
    
    @FunctionalInterface public interface Action4<T1, T2, T3, T4> extends IAction 
    { public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4); }
    
    @FunctionalInterface public interface Action5<T1, T2, T3, T4, T5> extends IAction 
    { public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5); }
    
    @FunctionalInterface public interface Action6<T1, T2, T3, T4, T5, T6> extends IAction 
    { public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6); }
    
    @FunctionalInterface public interface Action7<T1, T2, T3, T4, T5, T6, T7> extends IAction 
    { public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7); }
    
    @FunctionalInterface public interface Action8<T1, T2, T3, T4, T5, T6, T7, T8> extends IAction 
    { public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8); }
    
    @FunctionalInterface public interface Action9<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends IAction 
    { public void invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9); }

    public class ActionList<T extends IAction> extends ArrayList<T>
    {
        public void invokeAll(Action1<T> actionInvoker)
        {
            for (T action : this)
            {
                actionInvoker.invoke(action);
            }
        }
    }

    public class ActionHashMap<TKey, T extends IAction> extends HashMap<TKey, T>
    {
        public void invokeAll(Action1<T> actionInvoker)
        {
            for (T action : this.values())
            {
                actionInvoker.invoke(action);
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public abstract interface IFunc<TReturn> { }

    @FunctionalInterface public interface Func extends IFunc<Object>
    { public Object invoke(Object... args); }

    @FunctionalInterface public interface Func0<TReturn> extends IFunc<TReturn>
    { public TReturn invoke(); }
    
    @FunctionalInterface public interface Func1<TReturn, T1> extends IFunc<TReturn> 
    { public TReturn invoke(T1 arg1); }
    
    @FunctionalInterface public interface Func2<TReturn, T1, T2> extends IFunc<TReturn> 
    { public TReturn invoke(T1 arg1, T2 arg2); }
    
    @FunctionalInterface public interface Func3<TReturn, T1, T2, T3> extends IFunc<TReturn> 
    { public TReturn invoke(T1 arg1, T2 arg2, T3 arg3); }
    
    @FunctionalInterface public interface Func4<TReturn, T1, T2, T3, T4> extends IFunc<TReturn>
    { public TReturn invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4); }
    
    @FunctionalInterface public interface Func5<TReturn, T1, T2, T3, T4, T5> extends IFunc<TReturn>
    { public TReturn invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5); }
    
    @FunctionalInterface public interface Func6<TReturn, T1, T2, T3, T4, T5, T6> extends IFunc<TReturn> 
    { public TReturn invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6); }
    
    @FunctionalInterface public interface Func7<TReturn, T1, T2, T3, T4, T5, T6, T7> extends IFunc<TReturn> 
    { public TReturn invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7); }
    
    @FunctionalInterface public interface Func8<TReturn, T1, T2, T3, T4, T5, T6, T7, T8> extends IFunc<TReturn> 
    { public TReturn invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8); }
    
    @FunctionalInterface public interface Func9<TReturn, T1, T2, T3, T4, T5, T6, T7, T8, T9> extends IFunc<TReturn> 
    { public TReturn invoke(T1 arg1, T2 arg2, T3 arg3, T4 arg4, T5 arg5, T6 arg6, T7 arg7, T8 arg8, T9 arg9); }

    public class FuncList<T extends IAction> extends ArrayList<T>
    {
        public void invokeAll(Action1<T> actionInvoker)
        {
            for (T action : this)
            {
                actionInvoker.invoke(action);
            }
        }
    }

    public class FuncHashMap<TKey, T extends IFunc<?>> extends HashMap<TKey, T>
    {
        public void invokeAll(Action1<T> funcInvoker)
        {
            for (T action : this.values())
            {
                funcInvoker.invoke(action);
            }
        }
    }
}
