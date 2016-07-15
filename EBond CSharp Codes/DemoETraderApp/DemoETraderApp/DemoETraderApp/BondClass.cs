using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DemoETraderApp
{
    public class BondClass
    {
        public int bondId { get; set; }
        public string ISIN { get; set; }
        public DateTime purchaseDate { get; set; }
        public string status { get; set; }
        public double accruedAmount { get; set; }
        public double settlementAmount { get; set; }
        public double purchasedPrice { get; set; }
        public string profit_Loss { get; set; }
        public int quantity { get; set; }

        public BondClass(int bondId,string ISIN,DateTime purchaseDate,string status,
            double accruedAmount, double settlementAmount, double purchasedPrice, string profit_Loss, int quantity)
        {
            this.bondId = bondId;
            this.ISIN = ISIN;
            this.purchaseDate = purchaseDate;
            this.status = status;
            this.accruedAmount = accruedAmount;
            this.settlementAmount = settlementAmount;
            this.purchasedPrice = purchasedPrice;
            this.profit_Loss = profit_Loss;
            this.quantity = quantity;
        }
       
    }
}
