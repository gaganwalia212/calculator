package com.example.lab5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var isLastSymbol=true
    var isLastOpen=false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        input.showSoftInputOnFocus=false;

        ac.setOnClickListener {
            input.setText("")
            output.setText("")
        }
        
        calculate.setOnClickListener {
            if(isLastSymbol)
                return@setOnClickListener
            var no=solvePostfix(input.text.toString())
            output.text=no.toString()
        }

        del.setOnClickListener {
            var str=input.text.toString()

            var n=str.length
            if(str==="")
            return@setOnClickListener
            str=str.dropLast(1)
            input.text= Editable.Factory.getInstance().newEditable(str)
            n=str.length
            if(n==0)
            {
                isLastSymbol=true
                return@setOnClickListener
            }


            if(str[n-1]=='+'||str[n-1]=='/'||str[n-1]=='+'||str[n-1]=='x')
                isLastSymbol=true;
            else isLastSymbol=false




        }




    }

    fun convertToPostfix(str:String):String{
        var inPrecedence= hashMapOf<Char,Int>('+' to 2,'-' to 2,'x' to 4,'/' to 4)
        var outPrecedence= hashMapOf<Char,Int>('+' to 1,'-' to 1,'x' to 3,'/' to 3)
        var s=Stack<Char>()
        var i=0;
        var ans=""
        while(i<str.length)
        {
            if(str[i]=='+'||str[i]=='-'||str[i]=='x'||str[i]=='/')
            {
                if(s.empty()|| outPrecedence[str[i]]!! > inPrecedence[s.peek()]!!)
                {
                    s.push(str[i])
                    i++;
                }
                else
                {

                    ans+=s.pop()
                }

            }
            else{
                ans+=str[i];
                i++;
            }

        }
        while(!s.empty())
            ans+=s.pop();
        return ans;



    }
    fun solvePostfix(s:String):Double
    {
        var str=(convertToPostfix(s))
        var st=Stack<Double>()
        var i=0;
        while(i<str.length)
        {
            if(str[i]=='x')
            {
                var no2=st.pop();
                var no1=st.pop();
                st.push(no1*no2)

            }
            else if(str[i]=='+')
            {
                var no2=st.pop();
                var no1=st.pop();
                st.push(no1+no2)

            }
            else if(str[i]=='-')
            {
                var no2=st.pop();
                var no1=st.pop();
                st.push(no1-no2)

            }
            else if(str[i]=='/')
            {
                var no2=st.pop();
                var no1=st.pop();
                st.push(no1/no2)

            }
            else{
                st.push((str[i]-'0').toDouble() )

            }
            i++;
        }



        return st.pop();
    }

    fun numberClick(v: View)
    {
        var s=
            when(v.id)
            {
                R.id.zero->{"0"}
                R.id.one->{"1"}
                R.id.two->{"2"}
                R.id.three->{"3"}
                R.id.four->{"4"}
                R.id.five->{"5"}
                R.id.six->{"6"}
                R.id.seven->{"7"}
                R.id.eight->{"8"}
                R.id.nine->{"9"}
                else->""
            }


        input.text= Editable.Factory.getInstance().newEditable(input.text.toString()+s)
        isLastSymbol=false


    }
    fun operationClick(v:View)
    {
        if(isLastSymbol)
            return;



        var s=
            when(v.id)
            {
                R.id.add->{"+"}
                R.id.divide->{"/"}
                R.id.multiply->{"x"}
                R.id.subtract->{"-"}

                else->""
            }


        input.text= Editable.Factory.getInstance().newEditable(input.text.toString()+s)
        isLastSymbol=true


    }
}