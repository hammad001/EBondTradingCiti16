using System;
using System.Collections.Generic;
using System.Linq;
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

namespace f1rst
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class Pagination : Window
    {
        List<Record> fullList = new List<Record>();//Global
        List<Record> populatedList = new List<Record>();
        int pageSize = 10;
        public Pagination()
        {
            //InitializeComponent();


            for (int i = 0; i < 300; i++)
            {
                Record tempRecord = new Record();
                tempRecord.Id = i + 1;
                tempRecord.entry = "Record #" + (i + 1);
                fullList.Add(tempRecord);

            }
            for (int i = 0; i < pageSize; i++)
            {
                populatedList.Add(fullList[i]);//fetchFrom to fetchFrom+pageSize

            }
            recordsTable.ItemsSource = populatedList;
            PageNumber.Maximum = fullList.Count() / pageSize;

        }



        private void PageNumber_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            //int recTotal = Convert.ToInt16(numRecords.Text);
            int recTotal = fullList.Count;
            //MessageBox.Show(""+recTotal);
            //int pageSize = 10;
            int numPages = 0;//total number of pages
            if (recTotal % pageSize == 0)
            {
                numPages = (recTotal / pageSize);//no remainder records, hence last page not needed
            }
            else
            {
                numPages = (recTotal / pageSize) + 1;// +1 for remainder records
            }

            PageNumber.Maximum = numPages - 1;//slider starts from 0, not 1
            int pageNum = Convert.ToInt16(PageNumber.Value) + 1;//pageNum is not 0
            PageVal.Text = "" + pageNum;//display page Number
            int fetchFrom = (pageSize * (pageNum - 1) + 1);
            int fetchTo = 0;//assigned below

            if (pageNum * pageSize > recTotal)//when we're on last page
            {
                fetchTo = recTotal;
            }
            else
            {
                fetchTo = pageNum * pageSize;
            }
            recFrom.Text = "From " + fetchFrom;
            recTo.Text = "To " + fetchTo;

            populatedList = new List<Record>();
            //MessageBox.Show(""+fullList[4].entry);
            for (int i = 0; i < pageSize; i++)
            {
                populatedList.Add(fullList[fetchFrom + i - 1]);//fetchFrom to fetchFrom+pageSize

            }
            recordsTable.ItemsSource = populatedList;
        }

        private void previousPage_Click(object sender, RoutedEventArgs e)
        {
            PageNumber.Value--;
        }

        private void nextPage_Click(object sender, RoutedEventArgs e)
        {
            PageNumber.Value++;
        }
    }
    public class Record
    {
        public int Id { get; set; }

        public string entry { get; set; }


    }

}
