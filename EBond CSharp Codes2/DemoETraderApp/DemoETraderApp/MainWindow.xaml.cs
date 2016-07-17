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


namespace DemoETraderApp
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    /// 
       

    public partial class MainWindow : Window
    {
        bool clear = false;
        List<EBond> compareList;
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
                dataGridForComparison.Opacity = 100;
                EBond sentebond = (EBond)Bondslstbx.SelectedItem;
                dataGridForComparison.ItemsSource = bondsetup(sentebond);
//               dataGridfrbonds.ItemsSource= bondsetup(sentebond);
               //dataGridfrbonds.Items.Add(sentebond);
                buttonfrBT.IsEnabled = true;
                compareButton.IsEnabled = true;                
            }
            
           // Bondslstbx.MouseDoubleClick

        }

        private void OpenTBWTab(object sender, RoutedEventArgs e)
        {
           
            tabControl.SelectedItem = TBWTab;
            // Load events...(later..)
           
            
        }

        private void BondListSetup(object sender, RoutedEventArgs e)
        {
            // Bondlist Setup
            // string msg = "{\"isin\":\"IN126\",\"bondName\":\"saleem\"}";
            // MessageBox.Show(CRfrom.Text);
           
            
            string mdto = MDto.SelectedDate.Value.ToShortDateString();
            string searchstring = "{\"isin\":\"" + txtbxisin.Text + "\",\"couponRateFrom\":\"" + CRfrom.Text +
                "\",\"couponRateTo\":\"" + CRto.Text + "\",\"frequency\":\"" + comboBoxFrequency.SelectedItem.ToString() +
                "\",\"currency\":\"" + comboBox1.SelectedItem.ToString() + "\",\"creditRating\":\"" + CreditRatingBx.SelectedItem.ToString() +
                "\",\"yeildFrom\":\"" + txtbxYield.Text+ "\",\"yeildTo\":\"" + yeildTo.Text + "\",\"lastPriceFrom\":\"" +
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
            dataGridForComparison.ItemsSource = null;
            foreach(EBond ebond in EBonds)
            {
                Bondslstbx.Items.Add(ebond);
                // help += ebond.ToString()+ "\n";
               
            }
            // MessageBox.Show(help);

        }

        public double func(double couponRate, double cleanPrice, double faceValue, double couponFrequency, int time)
        {
            double couponPayment = (faceValue * couponRate) / 100;
            return faceValue - (cleanPrice * (Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency * time))) + ((couponPayment * couponFrequency) / couponRate) * (Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency * time)) - 1));
        }

        public double funcder(double couponRate, double cleanPrice, double faceValue, double couponFrequency, int time)
        {
            double couponPayment = (faceValue * couponRate) / 100;
            return -(cleanPrice * time * Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency * time) - 1)) - (couponPayment * couponRate * Math.Pow(1 + (couponRate / couponFrequency), (couponFrequency * time - 1))) / (Math.Pow(couponRate, 2)) + (couponPayment * couponRate) / (Math.Pow(couponRate, 2));

        }

        public double calculateYield(double couponRate, double cleanPrice, double faceValue,  double couponFrequency, int time)
        {
            double couponPayment = (faceValue * couponRate) / 100;


            double temp, yield;
            double limit = 0.0001;
            double deltaY = 0.1;
            while (deltaY > limit)
            {
                temp = couponRate - (func(couponRate, 91.89255, 100, 1, 13) / funcder(couponRate, 91.89255, 100, 1, 13));
                deltaY = couponRate - temp;
                couponRate = temp;
                MessageBox.Show(deltaY.ToString() + "," + couponRate.ToString());
            }
            yield = couponRate * 100;

            return yield;
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
            comboBox1.Items.Add("");
            comboBox1.Items.Add("USD");
            comboBox1.Items.Add("INR");
            comboBox1.Items.Add("GBP");
            comboBox1.Items.Add("EUR");

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

        private void button_Click(object sender, RoutedEventArgs e)
        {

        }

        private void Populate(object sender, RoutedEventArgs e)
        {
            var clie = new WebClient();
            // cli.Headers[HttpRequestHeader.ContentType] = "application/json";
            string blotter_populate = clie.DownloadString("http://192.168.137.37:8080/EBondTraderWeb/rest/bond/blotter");

            JavaScriptSerializer js = new JavaScriptSerializer();
            List<Blotter> context = (List<Blotter>)js.Deserialize(blotter_populate, typeof(List<Blotter>));
            /*Stream blotterlist = StringToStream(blotter_populate);
            StreamReader reader = new StreamReader(blotterlist);
            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Blotter[]));
            EBond[] context = (EBond[])serializer.ReadObject(EBond);*/

            //           datagridblotter.ItemsSource = context.BuySell;


            //json serializer
            //            DataContractJsonSerializer serializer = new DataContractJsonSerializer(typeof(Blotter[]));
            //           Blotter[] bookedbondlist = (Blotter[])serializer.ReadObject(blotterlist);

           datagridblotter.ItemsSource = context;
        }

        private void compareButton_Click(object sender, RoutedEventArgs e)
        {
            clearButton.IsEnabled = true;
            dataGridForComparison.Opacity = 100;

            //       EBond bond1 =  (EBond)dataGridForComparison.Items.GetItemAt(0);
            //      EBond bond2 = (EBond)Bondslstbx.SelectedItem;
            compareList = new List<EBond>();
            compareList.Add((EBond)dataGridForComparison.Items.GetItemAt(0));
            compareList.Add((EBond)Bondslstbx.SelectedItem);
            dataGridForComparison.ItemsSource = compareList;
            double yield = calculateYield(0.00827,91.89255, 100, 1, 13) ;
            MessageBox.Show(yield.ToString());
        }

        private void SearchTermTextBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            string searchTerm = SearchTermTextBox.Text;

        }

        private void clearButton_Click(object sender, RoutedEventArgs e)
        {
            clear = true;
            compareList = new List<EBond>();
            EBond bond = (EBond)dataGridForComparison.Items.GetItemAt(0);
            compareList.Add(bond);
            dataGridForComparison.ItemsSource = compareList;
        }
    }
}
