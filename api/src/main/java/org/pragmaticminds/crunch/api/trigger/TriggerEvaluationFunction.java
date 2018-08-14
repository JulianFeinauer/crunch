package org.pragmaticminds.crunch.api.trigger;

import org.pragmaticminds.crunch.api.pipe.EvaluationContext;
import org.pragmaticminds.crunch.api.pipe.EvaluationFunction;
import org.pragmaticminds.crunch.api.trigger.comparator.Supplier;
import org.pragmaticminds.crunch.api.trigger.extractor.EventExtractor;
import org.pragmaticminds.crunch.api.trigger.strategy.TriggerStrategy;
import org.pragmaticminds.crunch.api.values.TypedValues;
import org.pragmaticminds.crunch.events.Event;

/**
 * Simplifies the way to implement {@link EvaluationFunction} for typical tasks.
 * The {@link Supplier} pre evaluates the incoming data, so that the {@link TriggerStrategy} can decide
 * if further processing is required in the EventExtractor. The {@link EventExtractor} than generates Event resulting
 * from the incoming data.
 *
 * @author Erwin Wagasow
 * Created by Erwin Wagasow on 27.07.2018
 */
public class TriggerEvaluationFunction implements EvaluationFunction {
    private final TriggerStrategy triggerStrategy;
    private final EventExtractor  eventExtractor;
    
    /**
     * Main constructor of this class
     * //@param supplier simplifies the incoming data for the {@link TriggerStrategy}
     * @param triggerStrategy decides on the base of the simplified {@link Supplier} result, whether to
     *                        pass the incoming data to the {@link EventExtractor} or not
     * @param eventExtractor When the {@link TriggerStrategy} triggered further processing this class processes and
     *                       eventually extracts resulting {@link Event}s
     */
    private TriggerEvaluationFunction(
        TriggerStrategy triggerStrategy,
        EventExtractor eventExtractor
    ) {
        this.triggerStrategy = triggerStrategy;
        this.eventExtractor = eventExtractor;
    }
    
    /**
     * evaluates the incoming {@link TypedValues} from the {@link EvaluationContext} and passes the results
     * back to the collect method of the context
     *
     * @param ctx contains incoming data and a collector for the outgoing data
     */
    @Override
    public void eval(EvaluationContext ctx) {
        if(triggerStrategy.isToBeTriggered(ctx.get())){
            eventExtractor.process(ctx);
        }
    }
    
    public static class Builder {
        private TriggerStrategy triggerStrategy;
        private EventExtractor     eventExtractor;
    
        public Builder withTriggerStrategy(TriggerStrategy triggerStrategy){
            this.triggerStrategy = triggerStrategy;
            return this;
        }
        public Builder withEventExtractor(EventExtractor eventExtractor){
            this.eventExtractor = eventExtractor;
            return this;
        }
        public TriggerEvaluationFunction build(){
            assert triggerStrategy != null;
            assert eventExtractor != null;

            return new TriggerEvaluationFunction(triggerStrategy, eventExtractor);
        }
    }
}
