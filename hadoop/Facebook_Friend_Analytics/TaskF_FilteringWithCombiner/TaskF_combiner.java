import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class TaskF_combiner {

    public static void main(String[] args) throws Exception {

        if(args.length !=2){
            System.err.println("Invalid Command");
            System.err.println("Usage: TaskF <input path> <output path>");
            System.exit(0);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "TaskF_combiner");

        job.setJarByClass(TaskF_combiner.class);

        job.setMapperClass(MapperF.class);
        job.setCombinerClass(CombinerF.class);
        job.setReducerClass(ReducerF.class);


        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        //job.setSortComparatorClass(IntComparator.class);


        System.exit(job.waitForCompletion(true)?0:1);
    }
}
