using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DemoETraderApp
{

    /// <summary>
    /// Summary description for Class1
    /// </summary>
    public class NewtonRaphson
    {


        public NewtonRaphson()
        {
            //
            // TODO: Add constructor logic here
            //
        }

        double limit = 0.000001;

        static double func(double y)
        {
            double cleanPrice = 91.89;
            double couponPayment = 0.827;
            double couponFrequency = 1;
            double faceValue = 100;
            int time = 13;
            y = y / 100;
            // 1 + (y / couponFrequency))^(couponFrequency* time)

            return faceValue - (cleanPrice * (Math.Pow(1 + (y / couponFrequency), (couponFrequency * time))) + ((couponPayment * couponFrequency) / y) * (Math.Pow(1 + (y / couponFrequency), (couponFrequency * time)) - 1));
        }

        static double funcder(double y)
        {
            double cleanPrice = 91.89;
            double couponPayment = 0.827;
            double couponFrequency = 1;
            double faceValue = 100;
            int time = 13;
            y = y / 100;

            return -(cleanPrice * time * Math.Pow(1 + (y / couponFrequency), (couponFrequency * time) - 1)) - (couponPayment * y * Math.Pow(1 + (y / couponFrequency), (couponFrequency * time - 1))) / (Math.Pow(y, 2)) + (couponPayment * y) / (Math.Pow(y, 2));

        }



        public double calculateYield(double y)
        {

            double temp, yield;
            double deltaY = 1;
            while (deltaY > limit)
            {
                temp = y - (func(y) / funcder(y));
                deltaY = y - temp;
                y = temp;
            }
            yield = y * 100;

            return yield;
        }

    }

}
