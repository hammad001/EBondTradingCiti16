using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.IO;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Json;
using System.Web.Script.Serialization;
using System.Text.RegularExpressions;


namespace DemoETraderApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    /// 
   
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }
        
        private void ShowBSM(object sender, RoutedEventArgs e)
        {
           // Show();
        }

        public List<EBond> EBondList = new List<EBond>();
        List<EBond> BondList = new List<EBond>();

        private List<EBond> bondsetup(EBond obj)
        {
            BondList = new List<EBond>();
            
            BondList.Add(obj);
            return BondList;
        }
        private void selectbond(object sender, RoutedEventArgs e)
        {
            //int index = Bondslstbx.SelectedIndex;
            
            if (Bondslstbx.SelectedIndex == -1)
            {
                MessageBox.Show("Please select a bond");               
            }
            else
            {
                EBond sentebond = (EBond)Bondslstbx.SelectedItem;
               dataGridfrbonds.ItemsSource= bondsetup(sentebond);
               //dataGridfrbonds.Items.Add(sentebond);
                buttonfrBT.IsEnabled = true;
            }
            
        }
        public int GetIndex()
        {
            //int selectedBondIndex = Bondslstbx.SelectedIndex;
            string searchItem = Bondslstbx.SelectedItem.ToString();
            int index = 0;
            foreach(var item in ISINcbx.Items)
            {
                if (item.ToString() == searchItem) return index;
                index++;
            }
            return -1;
        }
        private void OpenTBWTab(object sender, RoutedEventArgs e)
        {
            
            EBond bookingbond = (EBond)Bondslstbx.SelectedItem;
            tabControl.SelectedItem = TBWTab;
            ISINcbx.SelectedItem = bookingbond;
            
            ISINcbx.SelectedIndex = GetIndex() ;            
            FVtxt.Text = bookingbond.faceValue.ToString();
            CRtxt.Text = bookingbond.couponRate.ToString();
            CFtxt.Text = bookingbond.couponFrequency.ToString();
            CrRtxt.Text = bookingbond.creditRating.ToString();
            LPtxt.Text = bookingbond.lastPrice.ToString();
            MDtxt.Text = bookingbond.maturityDate.ToString();
            SDtxt.Text = SettlementDate(bookingbond.settlementDays);
            
            
                                    
        }
        public string SettlementDate (int days)
        {
            DateTime Settlementdate = new DateTime();
            Settlementdate = DateTime.Today.AddDays(days);
            return Settlementdate.ToShortDateString();
        }

        private void BondListSetup(object sender, RoutedEventArgs e)
        {
            // Bondlist Setup
            // string msg = "{\"isin\":\"IN126\",\"bondName\":\"saleem\"}";
            // MessageBox.Show(CRfrom.Text);
            // to remove defaults entries in txt boxes
            if (txtbxisin.Text == "Enter Isin") txtbxisin.Text = "";
            if (CRfrom.Text == "From") CRfrom.Text = "";
            if (CRto.Text == "To") CRto.Text = "";
            if (txtbxYieldfrm.Text == "From") txtbxYieldfrm.Text = "";
            if (yeildTo.Text == "To") yeildTo.Text = "";
            if (LPfrom.Text == "From") LPfrom.Text = "";
            if (LPto.Text == "To") LPto.Text = "";
            // to remove defaults
           
            string mdto = MDto.SelectedDate.Value.ToShortDateString();
            string searchstring = "{\"isin\":\"" + txtbxisin.Text + "\",\"couponRateFrom\":\"" + CRfrom.Text +
                "\",\"couponRateTo\":\"" + CRto.Text + "\",\"frequency\":\"" + comboBoxFrequency.SelectedItem.ToString() +
                "\",\"currency\":\"" + Currencybx.SelectedItem.ToString() + "\",\"creditRating\":\"" + CreditRatingBx.SelectedItem.ToString() +
                "\",\"yeildFrom\":\"" + txtbxYieldfrm.Text+ "\",\"yeildTo\":\"" + yeildTo.Text + "\",\"lastPriceFrom\":\"" +
                LPfrom.Text+ "\",\"lastPriceTo\":\"" + LPto.Text + "\",\"maturityDateFrom\":\"" +MDfrom.SelectedDate.Value.ToShortDateString()
                + "\",\"maturityDateTo\":\"" + mdto + "\"}";
            // MessageBox.Show(searchstring);
            var cli = new WebClient();
            cli.Headers[HttpRequestHeader.ContentType] = "text/plain";
            string send = cli.UploadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/bsm", searchstring);

            //MessageBox.Show(send);

            //string response = cli.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond");
            Stream Ebondstream = StringToStream(send);
            StreamReader reader = new StreamReader(Ebondstream);
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(EBond[]));
            EBond[] EBonds = (EBond[])serializer.ReadObject(Ebondstream);
            //string help = "";
            Bondslstbx.Items.Clear();
            dataGridfrbonds.ItemsSource = null;
            foreach(EBond ebond in EBonds)
            {
                Bondslstbx.Items.Add(ebond);
                // help += ebond.ToString()+ "\n";
               
            }
           // MessageBox.Show(help);
           
        }

        private void Setup(object sender, RoutedEventArgs e)
        {
            CreditRatingBx.Items.Add("");
            CreditRatingBx.Items.Add("AAA");
            CreditRatingBx.Items.Add("AA");
            CreditRatingBx.Items.Add("A");
            CreditRatingBx.Items.Add("BBB");
            CreditRatingBx.Items.Add("BB");
            CreditRatingBx.Items.Add("B");
            CreditRatingBx.Items.Add("CCC");
            CreditRatingBx.Items.Add("CC");
            CreditRatingBx.Items.Add("C");
            comboBoxFrequency.Items.Add("");
            comboBoxFrequency.Items.Add("A");
            comboBoxFrequency.Items.Add("S");
            comboBoxFrequency.Items.Add("Q");
            Currencybx.Items.Add("");
            Currencybx.Items.Add("USD");
            Currencybx.Items.Add("INR");
            Currencybx.Items.Add("GBP");
            Currencybx.Items.Add("EUR");

        }
        public Stream StringToStream(string s)
        {
            MemoryStream stream = new MemoryStream();
            StreamWriter writer = new StreamWriter(stream);
            writer.Write(s);
            writer.Flush();
            stream.Position = 0;
            return stream;

        }

        private void LoadISIN(object sender, RoutedEventArgs e)
        {
            var client = new WebClient();
            string isinString = client.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/tbs");


            /*Stream isinstrm = StringToStream(isinString);
            StreamReader reader = new StreamReader(isinstrm);
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(EBond[]));
            EBond[] EBondslst = (EBond[])serializer.ReadObject(reader.BaseStream); */
            //string help3 = "";
            JavaScriptSerializer js = new JavaScriptSerializer();
            List<EBond> EBondslst = (List<EBond>)js.Deserialize(isinString, typeof(List<EBond>));

            foreach (EBond ebond in EBondslst)
            {
                ISINcbx.Items.Add(ebond.isin);

            }
           
        }

        private void selectEBond(object sender, MouseButtonEventArgs e)
        {
            EBond sentebond = (EBond)Bondslstbx.SelectedItem;
            dataGridfrbonds.ItemsSource = bondsetup(sentebond);
            //dataGridfrbonds.Items.Add(sentebond);
            buttonfrBT.IsEnabled = true;
        }

        private void ClearSearch(object sender, RoutedEventArgs e)
        {
            CreditRatingBx.SelectedIndex = 0;
            Currencybx.SelectedIndex = 0;
            comboBoxFrequency.SelectedIndex = 0;
            txtbxisin.Text = "";
            CRfrom.Text = "";
            CRto.Text = "";
            txtbxYieldfrm.Text = "";
            yeildTo.Text = "";
            LPfrom.Text = "";
            LPto.Text = "";
            MDfrom.SelectedDate = new DateTime(2000,07,16);
            MDto.SelectedDate = new DateTime(2051, 07, 16);
            Bondslstbx.Items.Clear();
        }

        private void ReselectBond(object sender, MouseButtonEventArgs e)
        {
            string nwisin = ISINcbx.SelectedItem.ToString();
            
            var user = new WebClient();
           
            string address = "http://192.168.137.37:8080/EBondTraderWeb/rest/bond/tbs?isin=" + nwisin;
            string searchedbond = user.DownloadString(address);
            Stream bondstream = StringToStream(searchedbond);
            StreamReader reader = new StreamReader(bondstream);
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(EBond[]));
            EBond[] EbondwithNwIsin = (EBond[])serializer.ReadObject(bondstream);
            EBond NwIsin = EbondwithNwIsin[0];

            FVtxt.Text = NwIsin.faceValue.ToString();
            CRtxt.Text = NwIsin.couponRate.ToString();
            CFtxt.Text = NwIsin.couponFrequency.ToString();
            CrRtxt.Text = NwIsin.creditRating.ToString();
            LPtxt.Text = NwIsin.lastPrice.ToString();
            MDtxt.Text = NwIsin.maturityDate.ToString();
            SDtxt.Text = SettlementDate(NwIsin.settlementDays);

        }

       

        public double CalculateSetlmntAmnt(double lastPice,int quantity)
        {
            return lastPice * quantity;
        }
        public int GetFreq()
        {
            int r = 1;
            if (CFtxt.Text == "S") r = 2;
            else if (CFtxt.Text == "Q") r = 4;

            return r;
        }
        public int Getyrs(string date)
        {
            DateTime md = DateTime.Parse(date);
            int yr=(md-DateTime.Today).Days/365;
            return yr;
        }
        public double CalculateCleanPrice()
        {
            double couponrate = double.Parse(CRtxt.Text);
            double FaceVal = double.Parse(FVtxt.Text);
            double C = couponrate * FaceVal;
            int k = GetFreq();
            double RbyK = (double.Parse(Yieldtxt.Text)) /k;
            int n = Getyrs(MDtxt.Text);
            
            double exp = (1 - ((1) /( Math.Pow((1 + RbyK) , (k * n))))) * (C / RbyK);
            double exp2 = (FaceVal) * ((1) / (Math.Pow((1 + RbyK), (k * n))));
            double CP = exp + exp2;
            return CP;

        }
        public int calcdatediff(DateTime issdDate)
        {
            int diff = (DateTime.Today.Date - issdDate).Days;
            
            return (diff%365);
            
        }
        // DateTime x = new DateTime();
        double lastPice;
        int quantity;
        string bondid;
        public double CalculateAcrdAmnt()
        {
            double couponrate = double.Parse(CRtxt.Text);
            double FaceVal = double.Parse(FVtxt.Text);
            double C = couponrate * FaceVal;
            EBond it=(EBond)Bondslstbx.SelectedItem;
            lastPice = it.lastPrice;
            quantity = int.Parse(Quantitybx.Text);
            bondid = it.bondId.ToString();
            DateTime isd = DateTime.Parse(it.issueDate);
            int issuedays = calcdatediff(isd);
            double res1 = (issuedays) % (365 / GetFreq());
            double res = (res1 / ((double)365 / GetFreq()))*C;
            return res;
        }
        public double CalDirtyPrice()
        {
            double ret;
            ret = CalculateCleanPrice() + CalculateAcrdAmnt();
            return ret;
        }

        private void showDefaultTxt(object sender, RoutedEventArgs e)
        {

            CPtxt.Text = "";
        }

        private void setupAmounts(object sender, KeyEventArgs e)
        {
            if(e.Key == Key.Enter)
            {
                string Cp = CalculateCleanPrice().ToString();
                CPtxt.Text = Cp;
                string Dp = CalDirtyPrice().ToString();
                DPtxt.Text = Dp;
                string AA = CalculateAcrdAmnt().ToString();
                AAtxt.Text = AA;
                string Sa = CalculateSetlmntAmnt(lastPice, quantity).ToString();
                SAtxt.Text = Sa;
            }
        }

        private void BuyTrade(object sender, RoutedEventArgs e)
        {
            var client = new WebClient();
            client.Headers[HttpRequestHeader.ContentType] = "text/plain";
            string post = "{" + "\"quantity\":\"" + Quantitybx.Text + "\",\"bondId\":\"" + bondid +
                "\",\"buySell\":\"B\"}";
           // MessageBox.Show(post);

            string rec = client.UploadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/tbs", post);
            MessageBox.Show(rec);
            if (rec == "{\"status\":\"Success\"}") MessageBox.Show("$$Your E-Bond has been successfully booked!$$");
            else MessageBox.Show("**Sorry, Your Bond has not been booked**");
        }

        private void SellTrade(object sender, RoutedEventArgs e)
        {
            var client = new WebClient();
            client.Headers[HttpRequestHeader.ContentType] = "text/plain";
            string post = "{" + "\"quantity\":\"" + Quantitybx.Text + "\",\"bondId\":\"" + bondid +
                "\",\"buySell\":\"S\"}";
            string rec = client.UploadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/tbs", post);
            //MessageBox.Show(rec);
            if (rec == "{\"status\":\"Success\"}") MessageBox.Show("$$Your E-Bond has been successfully booked!$$");
            else MessageBox.Show("**Sorry, Your Bond has not been booked**");
        }

        private void ISIN(object sender, RoutedEventArgs e)
        {
            txtbxisin.Text = "";
        }

        private void CR(object sender, RoutedEventArgs e)
        {
            CRfrom.Text = "";
        }

        private void CRGS(object sender, RoutedEventArgs e)
        {
            CRto.Text = "";
        }

        private void Yield(object sender, RoutedEventArgs e)
        {
            txtbxYieldfrm.Text = "";
        }

        private void Yieldto(object sender, RoutedEventArgs e)
        {
            yeildTo.Text = "";
        }

        private void LP(object sender, RoutedEventArgs e)
        {
            LPfrom.Text = "";
        }

        private void LPtoGs(object sender, RoutedEventArgs e)
        {
            LPto.Text = "";
        }

        //OtherTabCodes//

        private void Search(object sender, RoutedEventArgs e)
        {
            var clie = new WebClient();
            // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
            string blotter_populate = clie.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/blotter?isin=" + textBox_blotter.Text);

            JavaScriptSerializer js = new JavaScriptSerializer();
            List<Blotter> context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));


            datagridblotter.ItemsSource = context;
        }

        public string response;
        //private void Populate(object sender, RoutedEventArgs e)
        //{
        //    var clie = new WebClient();
        //    // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
        //    string blotter_populate = clie.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/blotter");

        //    JavaScriptSerializer js = new JavaScriptSerializer();
        //    List<Blotter> context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));
        //    /*Stream blotterlist = StringToStream(blotter_populate);
        //    StreamReader reader = new StreamReader(blotterlist);
        //    DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Blotter[]));
        //    EBond[] context = (EBond[])serializer.ReadObject(EBond);*/

        //    //           datagridblotter.ItemsSource = context.BuySell;


        //    //json serializer
        //    //            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Blotter[]));
        //    //           Blotter[] bookedbondlist = (Blotter[])serializer.ReadObject(blotterlist);

        //    datagridblotter.ItemsSource = context;
        //}



        private void textBox_blotter_GotFocus(object sender, RoutedEventArgs e)
        {
            textBox_blotter.Text = "";
        }

        private void test(object sender, RoutedEventArgs e)
        {
            var clie = new WebClient();
            // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
            string blotter_populate = clie.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/blotter");

            JavaScriptSerializer js = new JavaScriptSerializer();
            List<Blotter> context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));
            datagridblotter.ItemsSource = context;

        }

        private void ShowBookedBonds(object sender, KeyEventArgs e)
        {
            if (e.Key == Key.Enter)
            {
                var clie = new WebClient();
                // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
                string blotter_populate = clie.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/blotter?isin=" + textBox_blotter.Text);

                JavaScriptSerializer js = new JavaScriptSerializer();
                List<Blotter> context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));

                datagridblotter.ItemsSource = context;
            }
        }

        private void numbersOnly(object sender, TextCompositionEventArgs e)
        {

            decimal data ;
            e.Handled=!Decimal.TryParse(e.Text, out data);

            if (e.Text.ToString().Equals("."))
            {
                e.Handled = false;
             }

            

        }

          }
}
